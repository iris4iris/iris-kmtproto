package iris.kmtproto.crypto;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;

/** Aligned 8-byte load/store on {@code byte[]} without extra {@code --add-opens}. */
final class ByteLongs {
    private static final VarHandle H =
        MethodHandles.byteArrayViewVarHandle(long[].class, ByteOrder.LITTLE_ENDIAN);

    private ByteLongs() {}

    static long get(byte[] a, int i) {
        return (long) H.get(a, i);
    }

    static void set(byte[] a, int i, long v) {
        H.set(a, i, v);
    }
}
