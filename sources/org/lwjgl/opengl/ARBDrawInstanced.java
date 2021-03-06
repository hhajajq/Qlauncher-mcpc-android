package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.BufferChecks;
import org.lwjgl.MemoryUtil;

public final class ARBDrawInstanced {
    private ARBDrawInstanced() {
    }

    public static void glDrawArraysInstancedARB(int i, int i2, int i3, int i4) {
        int i5 = i;
        int i6 = i2;
        int i7 = i3;
        int i8 = i4;
        long j = GLContext.getCapabilities().glDrawArraysInstancedARB;
        BufferChecks.checkFunctionAddress(j);
        nglDrawArraysInstancedARB(i5, i6, i7, i8, j);
    }

    public static void glDrawElementsInstancedARB(int i, int i2, int i3, long j, int i4) {
        int i5 = i;
        int i6 = i2;
        int i7 = i3;
        long j2 = j;
        int i8 = i4;
        ContextCapabilities capabilities = GLContext.getCapabilities();
        long j3 = capabilities.glDrawElementsInstancedARB;
        BufferChecks.checkFunctionAddress(j3);
        GLChecks.ensureElementVBOenabled(capabilities);
        nglDrawElementsInstancedARBBO(i5, i6, i7, j2, i8, j3);
    }

    public static void glDrawElementsInstancedARB(int i, ByteBuffer byteBuffer, int i2) {
        int i3 = i;
        ByteBuffer byteBuffer2 = byteBuffer;
        int i4 = i2;
        ContextCapabilities capabilities = GLContext.getCapabilities();
        long j = capabilities.glDrawElementsInstancedARB;
        BufferChecks.checkFunctionAddress(j);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(byteBuffer2);
        nglDrawElementsInstancedARB(i3, byteBuffer2.remaining(), GL11.GL_UNSIGNED_BYTE, MemoryUtil.getAddress(byteBuffer2), i4, j);
    }

    public static void glDrawElementsInstancedARB(int i, IntBuffer intBuffer, int i2) {
        int i3 = i;
        IntBuffer intBuffer2 = intBuffer;
        int i4 = i2;
        ContextCapabilities capabilities = GLContext.getCapabilities();
        long j = capabilities.glDrawElementsInstancedARB;
        BufferChecks.checkFunctionAddress(j);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(intBuffer2);
        nglDrawElementsInstancedARB(i3, intBuffer2.remaining(), GL11.GL_UNSIGNED_INT, MemoryUtil.getAddress(intBuffer2), i4, j);
    }

    public static void glDrawElementsInstancedARB(int i, ShortBuffer shortBuffer, int i2) {
        int i3 = i;
        ShortBuffer shortBuffer2 = shortBuffer;
        int i4 = i2;
        ContextCapabilities capabilities = GLContext.getCapabilities();
        long j = capabilities.glDrawElementsInstancedARB;
        BufferChecks.checkFunctionAddress(j);
        GLChecks.ensureElementVBOdisabled(capabilities);
        BufferChecks.checkDirect(shortBuffer2);
        nglDrawElementsInstancedARB(i3, shortBuffer2.remaining(), GL11.GL_UNSIGNED_SHORT, MemoryUtil.getAddress(shortBuffer2), i4, j);
    }

    static native void nglDrawArraysInstancedARB(int i, int i2, int i3, int i4, long j);

    static native void nglDrawElementsInstancedARB(int i, int i2, int i3, long j, int i4, long j2);

    static native void nglDrawElementsInstancedARBBO(int i, int i2, int i3, long j, int i4, long j2);
}
