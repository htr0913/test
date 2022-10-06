//
// Created by 27889 on 2022/10/7.
//

#ifndef TEST_LOG_H
#define TEST_LOG_H
#ifdef __cplusplus
extern "C" {
#endif
#include <android/log.h>

#define LOG_TAG    "HTR_JNI"
#define ALOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG, __VA_ARGS__)
#define ALOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG, __VA_ARGS__)
#define ALOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG, __VA_ARGS__)
#define ALOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG, __VA_ARGS__)
#define ALOGF(...)  __android_log_print(ANDROID_LOG_FATAL,LOG_TAG, __VA_ARGS__)




#ifdef __cplusplus
}
#endif
#endif //TEST_LOG_H
