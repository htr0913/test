package com.htr.test.utils;

public class JNIUtil {
    static {
        System.loadLibrary("linear_list");
    }
    public static native long initArray(int length);
}
