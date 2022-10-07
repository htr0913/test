package com.htr.test.utils;

public class JNIUtil {
    static {
        System.loadLibrary("linear_list");
    }
    public static native long initArray(int length);
    public static native boolean appendArray(long nativeData, int val);
    public static native void showArray(long nativeData);
    public static native boolean insertArray(long nativeData, int index, int val);
    public static native void reverseArray(long nativeData);
    public static native boolean deleteArray(long nativeData, int index);
}
