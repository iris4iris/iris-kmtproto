package iris.kmtproto.crypto;

/**
 * IGE / CTR inner loops in Java with 8-byte xor (unaligned-safe).
 * Kotlin per-byte loops were the leftover IGE cost after AESCrypt.
 */
final class AesBlocks {
    private AesBlocks() {}

    static long get8(byte[] a, int i) {
        return (a[i] & 0xffL)
            | ((a[i + 1] & 0xffL) << 8)
            | ((a[i + 2] & 0xffL) << 16)
            | ((a[i + 3] & 0xffL) << 24)
            | ((a[i + 4] & 0xffL) << 32)
            | ((a[i + 5] & 0xffL) << 40)
            | ((a[i + 6] & 0xffL) << 48)
            | ((a[i + 7] & 0xffL) << 56);
    }

    static void put8(byte[] a, int i, long v) {
        a[i] = (byte) v;
        a[i + 1] = (byte) (v >>> 8);
        a[i + 2] = (byte) (v >>> 16);
        a[i + 3] = (byte) (v >>> 24);
        a[i + 4] = (byte) (v >>> 32);
        a[i + 5] = (byte) (v >>> 40);
        a[i + 6] = (byte) (v >>> 48);
        a[i + 7] = (byte) (v >>> 56);
    }

    static void ige(
        AesEcb aes,
        boolean encrypt,
        byte[] iv1,
        byte[] iv2,
        byte[] tmpIn,
        byte[] tmpOut,
        byte[] data,
        int start,
        int n,
        byte[] dest,
        int destOff
    ) {
        int end = start + n;
        int src = start;
        int dst = destOff;
        if (encrypt) {
            while (src < end) {
                put8(tmpIn, 0, get8(data, src) ^ get8(iv1, 0));
                put8(tmpIn, 8, get8(data, src + 8) ^ get8(iv1, 8));
                aes.block(tmpIn, 0, tmpOut, 0);
                long c0 = get8(tmpOut, 0) ^ get8(iv2, 0);
                long c1 = get8(tmpOut, 8) ^ get8(iv2, 8);
                put8(dest, dst, c0);
                put8(dest, dst + 8, c1);
                put8(iv2, 0, get8(data, src));
                put8(iv2, 8, get8(data, src + 8));
                put8(iv1, 0, c0);
                put8(iv1, 8, c1);
                src += 16;
                dst += 16;
            }
        } else {
            while (src < end) {
                put8(tmpIn, 0, get8(data, src) ^ get8(iv2, 0));
                put8(tmpIn, 8, get8(data, src + 8) ^ get8(iv2, 8));
                aes.block(tmpIn, 0, tmpOut, 0);
                long p0 = get8(tmpOut, 0) ^ get8(iv1, 0);
                long p1 = get8(tmpOut, 8) ^ get8(iv1, 8);
                put8(dest, dst, p0);
                put8(dest, dst + 8, p1);
                put8(iv1, 0, get8(data, src));
                put8(iv1, 8, get8(data, src + 8));
                put8(iv2, 0, p0);
                put8(iv2, 8, p1);
                src += 16;
                dst += 16;
            }
        }
    }

    /** Returns the new keystream offset (0..16). Mutates [counter] and [keystream]. */
    static int ctr(
        AesEcb aes,
        byte[] counter,
        byte[] keystream,
        int offset,
        byte[] src,
        int srcOff,
        byte[] dst,
        int dstOff,
        int len
    ) {
        int i = 0;
        while (i < len && offset != 16) {
            dst[dstOff + i] = (byte) (src[srcOff + i] ^ keystream[offset++]);
            i++;
        }
        while (i + 16 <= len) {
            aes.block(counter, 0, keystream, 0);
            incCtr(counter);
            int s = srcOff + i;
            int d = dstOff + i;
            put8(dst, d, get8(src, s) ^ get8(keystream, 0));
            put8(dst, d + 8, get8(src, s + 8) ^ get8(keystream, 8));
            i += 16;
            offset = 16;
        }
        while (i < len) {
            if (offset == 16) {
                aes.block(counter, 0, keystream, 0);
                offset = 0;
                incCtr(counter);
            }
            dst[dstOff + i] = (byte) (src[srcOff + i] ^ keystream[offset++]);
            i++;
        }
        return offset;
    }

    static void incCtr(byte[] block) {
        for (int i = 15; i >= 0; i--) {
            int next = (block[i] & 0xff) + 1;
            block[i] = (byte) next;
            if (next < 256) break;
        }
    }
}
