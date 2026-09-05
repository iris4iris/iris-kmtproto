package iris.kmtproto.crypto;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;

/**
 * HotSpot AES-NI without JNI-per-block: {@code com.sun.crypto.provider.AESCrypt}
 * ({@code implEncryptBlock} / {@code implDecryptBlock} are intrinsic).
 * Needs {@code --add-opens java.base/com.sun.crypto.provider=ALL-UNNAMED}.
 * If the package is closed, {@link #AVAILABLE} is false and {@link AesEcb} uses {@code Cipher}.
 */
final class AesNi {
    static final boolean AVAILABLE;

    private static final Constructor<?> CTOR;
    private static final MethodHandle INIT;
    private static final MethodHandle ENCRYPT;
    private static final MethodHandle DECRYPT;

    static {
        Constructor<?> ctor = null;
        MethodHandle init = null;
        MethodHandle enc = null;
        MethodHandle dec = null;
        boolean ok = false;
        try {
            Class<?> cls = Class.forName("com.sun.crypto.provider.AESCrypt");
            ctor = cls.getDeclaredConstructor();
            ctor.setAccessible(true);
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(cls, MethodHandles.lookup());
            init = lookup.findVirtual(
                cls,
                "init",
                MethodType.methodType(void.class, boolean.class, String.class, byte[].class)
            );
            MethodType block = MethodType.methodType(void.class, byte[].class, int.class, byte[].class, int.class);
            enc = lookup.findVirtual(cls, "encryptBlock", block);
            dec = lookup.findVirtual(cls, "decryptBlock", block);
            Object probe = ctor.newInstance();
            init.invoke(probe, false, "AES", new byte[32]);
            byte[] in = new byte[16];
            byte[] out = new byte[16];
            enc.bindTo(probe).invokeExact(in, 0, out, 0);
            ok = true;
        } catch (Throwable ignored) {
            ctor = null;
            init = null;
            enc = null;
            dec = null;
        }
        CTOR = ctor;
        INIT = init;
        ENCRYPT = enc;
        DECRYPT = dec;
        AVAILABLE = ok;
    }

    private final Object crypt;
    private final MethodHandle block;
    private final boolean decrypting;

    AesNi(boolean encrypt) {
        try {
            crypt = CTOR.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
        decrypting = !encrypt;
        // Bind once: rebinding in init() (every MTProto frame) deopts C2.
        block = (encrypt ? ENCRYPT : DECRYPT).bindTo(crypt);
    }

    void init(byte[] key) {
        try {
            INIT.invoke(crypt, decrypting, "AES", key);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    void block(byte[] src, int srcOff, byte[] dst, int dstOff) {
        try {
            block.invokeExact(src, srcOff, dst, dstOff);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }
}
