#!/usr/bin/env python3
"""Generate Kotlin Bot API types from schema/api.min.json.

Source: PaulSonOfLars/telegram-bot-api-spec (vendored). Run from the :bot module:

    python3 gen/generate_bot_api.py
"""

import json
import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
SCHEMA = ROOT / "schema" / "api.min.json"
OUT = ROOT / "src" / "commonMain" / "kotlin" / "iris" / "kmtproto" / "bot" / "gen"

KEYWORDS = {
    "as", "break", "class", "continue", "do", "else", "false", "for", "fun", "if",
    "in", "interface", "is", "null", "object", "package", "return", "super", "this",
    "throw", "true", "try", "typealias", "typeof", "val", "var", "when", "while",
}
SCALARS = {"Integer", "String", "Boolean", "Float"}


def camel(name: str) -> str:
    parts = name.split("_")
    out = parts[0] + "".join(p[:1].upper() + p[1:] for p in parts[1:] if p)
    if out in KEYWORDS:
        return f"`{out}`"
    return out


def fn(name: str) -> str:
    return name[0].lower() + name[1:] + "FromMap"


def literal_of(field: dict) -> str | None:
    if field.get("types") != ["String"]:
        return None
    desc = field.get("description") or ""
    m = re.search(r'always "([^"]+)"', desc)
    if m:
        return m.group(1)
    m = re.search(r"must be ([A-Za-z0-9_]+)\b(?! of)", desc)
    if m:
        return m.group(1)
    return None


def disc_of(type_def: dict) -> tuple[str, str] | None:
    found = []
    for field in type_def.get("fields") or []:
        lit = literal_of(field)
        if lit:
            found.append((field["name"], lit))
    if not found:
        return None
    for name, lit in found:
        if name in ("type", "status", "source"):
            return name, lit
    return found[0]


def kotlin_ref(names: list[str], unions: dict[str, str]) -> str:
    if names == ["Integer", "String"] or names == ["String", "Integer"]:
        return "LongOrString"
    if len(names) > 1:
        return unions[tuple(names)]
    return kotlin_one(names[0], unions)


def kotlin_one(t: str, unions: dict[str, str]) -> str:
    if t == "Integer":
        return "Long"
    if t == "Float":
        return "Double"
    if t in ("String", "Boolean"):
        return t
    if t.startswith("Array of "):
        return f"List<{kotlin_one(t[len('Array of '):], unions)}>"
    return t


def decode_one(t: str, raw: str, unions: dict[str, str]) -> str:
    if t == "Integer":
        return f"botLong({raw})"
    if t == "String":
        return f"botString({raw})"
    if t == "Boolean":
        return f"botBool({raw})"
    if t == "Float":
        return f"botDouble({raw})"
    if t.startswith("Array of "):
        inner = decode_one(t[len("Array of "):], "it", unions)
        return f"botList({raw}) {{ {inner} }}"
    return f"{fn(t)}({raw})"


def decode_expr(names: list[str], raw: str, unions: dict[str, str]) -> str:
    if names == ["Integer", "String"] or names == ["String", "Integer"]:
        return f"LongOrString.of({raw})"
    if len(names) == 1:
        return decode_one(names[0], raw, unions)
    return f"{fn(unions[tuple(names)])}({raw})"


def is_primitive(names: list[str]) -> bool:
    return len(names) == 1 and names[0] in SCALARS


def is_list(names: list[str]) -> bool:
    return len(names) == 1 and names[0].startswith("Array of ")


def main() -> None:
    spec = json.loads(SCHEMA.read_text())
    types: dict = spec["types"]
    version = spec["version"]
    date = spec["release_date"]

    parents = {n for n, t in types.items() if t.get("subtypes")}
    class_names = set(types)

    unions: dict[tuple, str] = {}
    for t in types.values():
        for field in t.get("fields") or []:
            names = field["types"]
            key = tuple(names)
            if len(names) < 2 or key in unions:
                continue
            if names == ["Integer", "String"] or names == ["String", "Integer"]:
                continue
            if not all(n in class_names for n in names):
                raise SystemExit(f"unhandled union {t['name']}.{field['name']}: {names}")
            parent_sets = []
            for n in names:
                pset = set(types[n].get("subtype_of") or [])
                parent_sets.append(pset)
            common = set.intersection(*parent_sets) if parent_sets else set()
            common = {p for p in common if p in parents}
            if len(common) == 1:
                unions[key] = next(iter(common))
            else:
                unions[key] = field["name"][:1].upper() + camel(field["name"])[1:] + "Of" + t["name"]

    # RichText gains two carriers for the spec's String / Array subtypes.
    extra_ifaces = sorted({v for v in unions.values() if v not in parents and v not in types})

    OUT.mkdir(parents=True, exist_ok=True)
    for old in OUT.glob("*.kt"):
        old.unlink()

    header = (
        f"// Generated from {version} ({date}). Do not edit.\n"
        "// Regenerate: python3 gen/generate_bot_api.py\n"
        "package iris.kmtproto.bot\n\n"
    )

    type_chunks = [header]
    for iface in extra_ifaces:
        type_chunks.append(f"sealed interface {iface}\n\n")

    for name in sorted(parents):
        subs = [s for s in types[name]["subtypes"] if s in class_names]
        weird = [s for s in types[name]["subtypes"] if s not in class_names]
        note = ""
        if weird:
            note = " Non-object values: " + ", ".join(weird) + "."
        href = types[name].get("href") or ""
        type_chunks.append(f"/** [{name}]({href}).{note} */\nsealed interface {name}\n\n")

    for name in sorted(types):
        if name in parents:
            continue
        t = types[name]
        href = t.get("href") or ""
        supers = list(t.get("subtype_of") or [])
        for field in t.get("fields") or []:
            key = tuple(field["types"])
            iface = unions.get(key)
            if iface and iface not in parents and iface not in supers and iface not in types:
                if name in field["types"]:
                    supers.append(iface)
        # classes listed in a union must implement the synthetic iface
        for key, iface in unions.items():
            if name in key and iface not in parents and iface not in supers:
                supers.append(iface)
        super_s = (" : " + ", ".join(supers)) if supers else ""
        fields = t.get("fields") or []
        if not fields:
            if name == "InputFile":
                type_chunks.append(
                    f"/** [{name}]({href}). Path, file_id, or attach:// name. */\n"
                    f"data class InputFile(\n    val value: String = \"\",\n)\n\n"
                )
            else:
                type_chunks.append(f"/** [{name}]({href}). */\ndata object {name}\n\n")
            continue
        lines = [f"/** [{name}]({href}). */\ndata class {name}("]
        for i, field in enumerate(fields):
            ktype = kotlin_ref(field["types"], unions)
            optional = not field["required"] or not (is_primitive(field["types"]) or is_list(field["types"]))
            if is_primitive(field["types"]) and field["required"]:
                default = {"Integer": "0", "String": '""', "Boolean": "false", "Float": "0.0"}[field["types"][0]]
                decl = f"{ktype} = {default}"
            elif is_list(field["types"]) and field["required"]:
                decl = f"{ktype} = emptyList()"
            else:
                decl = f"{ktype}? = null"
                optional = True
            comma = "," if i < len(fields) - 1 else ","
            lines.append(f"    val {camel(field['name'])}: {decl}{comma}")
        lines.append(f"){super_s}\n")
        type_chunks.append("\n".join(lines) + "\n")

    if "RichText" in parents:
        type_chunks.append(
            "/** Plain string used where the spec allows a [RichText] value to be a String. */\n"
            "data class RichTextPlain(\n    val text: String = \"\",\n) : RichText\n\n"
            "/** List used where the spec allows a [RichText] value to be an array. */\n"
            "data class RichTextParts(\n    val parts: List<RichText> = emptyList(),\n) : RichText\n\n"
        )

    (OUT / "Types.kt").write_text("".join(type_chunks))

    dec = [header]

    def field_names(tname: str) -> list[str]:
        return [f["name"] for f in types[tname].get("fields") or []]

    def emit_struct(name: str) -> None:
        t = types[name]
        fields = t.get("fields") or []
        if not fields:
            if name == "InputFile":
                dec.append(
                    "fun inputFileFromMap(raw: Any?): InputFile? = when (raw) {\n"
                    "    is String -> InputFile(raw)\n"
                    "    is Number -> InputFile(raw.toString())\n"
                    "    else -> null\n"
                    "}\n\n"
                )
            else:
                dec.append(
                    f"fun {fn(name)}(raw: Any?): {name}? =\n"
                    f"    if (raw is Map<*, *> || raw == true) {name} else null\n\n"
                )
            return
        ret = name
        args = []
        for field in fields:
            raw = f'm["{field["name"]}"]'
            expr = decode_expr(field["types"], raw, unions)
            if is_primitive(field["types"]) and field["required"]:
                default = {"Integer": "0", "String": '""', "Boolean": "false", "Float": "0.0"}[field["types"][0]]
                expr = f"{expr} ?: {default}"
            elif is_list(field["types"]) and field["required"]:
                expr = f"{expr} ?: emptyList()"
            args.append(f"        {camel(field['name'])} = {expr},")
        body = "\n".join(args)
        dec.append(
            f"fun {fn(name)}(raw: Any?): {ret}? {{\n"
            f"    val m = raw as? Map<*, *> ?: return null\n"
            f"    return {name}(\n{body}\n    )\n"
            f"}}\n\n"
        )

    for name in sorted(types):
        if name in parents:
            continue
        emit_struct(name)

    for iface in extra_ifaces:
        members = [n for key, v in unions.items() if v == iface for n in key]
        # unique preserve order
        seen = []
        for n in members:
            if n not in seen:
                seen.append(n)
        alts = []
        for n in seen:
            fields = ", ".join(f'"{f}"' for f in field_names(n))
            alts.append(f'        BotAlt(setOf({fields})) {{ {fn(n)}(it) }},')
        dec.append(
            f"fun {fn(iface)}(raw: Any?): {iface}? {{\n"
            f"    val m = raw as? Map<*, *> ?: return null\n"
            f"    return listOf(\n" + "\n".join(alts) + "\n    ).bestMatch(m)\n"
            f"}}\n\n"
        )

    for name in sorted(parents):
        if name == "RichText":
            subs = [s for s in types[name]["subtypes"] if s in class_names]
            branches = []
            groups: dict[str, list[tuple[str, str]]] = {}
            for sub in subs:
                d = disc_of(types[sub])
                if not d:
                    continue
                groups.setdefault(d[1], []).append((sub, d[0]))
            for lit, group in groups.items():
                if len(group) == 1:
                    branches.append(f'        "{lit}" -> {fn(group[0][0])}(raw)')
                else:
                    alts = []
                    for sub, _field in group:
                        fields = ", ".join(f'"{f}"' for f in field_names(sub))
                        alts.append(f'            BotAlt(setOf({fields})) {{ {fn(sub)}(it) }},')
                    branches.append(
                        f'        "{lit}" -> listOf(\n' + "\n".join(alts) + "\n        ).bestMatch(m)"
                    )
            dec.append(
                "fun richTextFromMap(raw: Any?): RichText? = when (raw) {\n"
                "    is String -> RichTextPlain(raw)\n"
                "    is List<*> -> RichTextParts(raw.mapNotNull { richTextFromMap(it) })\n"
                "    is Map<*, *> -> when (raw[\"type\"] as? String) {\n"
                + "\n".join(branches)
                + "\n        else -> null\n    }\n    else -> null\n}\n\n"
            )
            continue
        subs = [s for s in types[name]["subtypes"] if s in class_names]
        discs = []
        nodisc = []
        for sub in subs:
            d = disc_of(types[sub])
            if d:
                discs.append((sub, d[0], d[1]))
            else:
                nodisc.append(sub)
        fields_used = {d[1] for d in discs}
        if discs and not nodisc and len(fields_used) == 1:
            field = next(iter(fields_used))
            groups: dict[str, list[str]] = {}
            for sub, _f, lit in discs:
                groups.setdefault(lit, []).append(sub)
            branches = []
            for lit, group in groups.items():
                if len(group) == 1:
                    branches.append(f'        "{lit}" -> {fn(group[0])}(raw)')
                else:
                    alts = []
                    for sub in group:
                        fields = ", ".join(f'"{f}"' for f in field_names(sub))
                        alts.append(f'            BotAlt(setOf({fields})) {{ {fn(sub)}(it) }},')
                    branches.append(
                        f'        "{lit}" -> listOf(\n' + "\n".join(alts) + "\n        ).bestMatch(m)"
                    )
            dec.append(
                f"fun {fn(name)}(raw: Any?): {name}? {{\n"
                f"    val m = raw as? Map<*, *> ?: return null\n"
                f"    return when (m[\"{field}\"] as? String) {{\n"
                + "\n".join(branches)
                + "\n        else -> null\n    }\n}\n\n"
            )
        else:
            alts = []
            for sub in subs:
                fields = ", ".join(f'"{f}"' for f in field_names(sub))
                alts.append(f'        BotAlt(setOf({fields})) {{ {fn(sub)}(it) }},')
            dec.append(
                f"fun {fn(name)}(raw: Any?): {name}? {{\n"
                f"    val m = raw as? Map<*, *> ?: return null\n"
                f"    return listOf(\n" + "\n".join(alts) + "\n    ).bestMatch(m)\n"
                f"}}\n\n"
            )

    (OUT / "Decode.kt").write_text("".join(dec))
    print(f"{version} types={len(types)} parents={len(parents)} unions={len(unions)} -> {OUT}")


if __name__ == "__main__":
    main()
