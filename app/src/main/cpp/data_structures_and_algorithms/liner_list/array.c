//
// Created by 27889 on 2022/10/4.
//

#include <stdbool.h>
#include <malloc.h>
#include <jni.h>
#include "../../log.h"
#include <android/log.h>

struct Array
{
    int* pBase; /* the first address of array */
    int length;
    int count; /* valid element count */
};

size_t init_arr(struct Array * pArr, int length);
bool insert_arr(struct Array* pArr, int index, int val);
bool delete_arr(struct Array* pArr, int index);
void reverse_arr(struct Array* pArr);
bool is_empty(struct Array * pArr);
bool is_full(struct Array* pArr);
bool append_arr(struct Array * pArr, int val);
void show_arr(struct Array* pArr);

// It's necessary to pass a pArr, so it can escape from this function
size_t init_arr(struct Array * pArr, int length)
{
    pArr->pBase = (int*) malloc(sizeof(int) * length);
    if (pArr->pBase == NULL)
    {
        ALOGI("init array failed!");
        return NULL;
    }

    ALOGI("init array:%zu, length:%d/n sizeof struct Array:%d, sizeof *pArr:%d, pBase:%zu",
          (size_t)pArr, length, sizeof(struct Array), sizeof(*pArr), (size_t)&(pArr->pBase));
    pArr->length = length;
    pArr->count = 0;
    return (size_t) pArr;
}

bool is_empty(struct Array *pArr)
{
    return 0 == pArr->count;
}

void show_arr(struct Array* pArr)
{
    if (is_empty(pArr)) ALOGI("empty array!");
    for (int i = 0; i < pArr->count; i++)
    {
        ALOGI("%d", pArr->pBase[i]);
    }
    ALOGI("Array pointer:%zu", (size_t)pArr);
}

bool append_arr(struct Array * pArr, int val)
{
    if(is_full(pArr)) return false;
    pArr->pBase[pArr->count] = val;
    ALOGI("%d appended at %d",val, pArr->count);
    pArr->count++;

    return true;
}

bool is_full(struct Array* pArr)
{
    return pArr->count == pArr->length;
}

/*
 * insert val at index
 *
 * */
bool insert_arr(struct Array* pArr, int index, int val)
{
    if (is_full(pArr))
    {
        ALOGI("failed to insert a full array");
        return false;
    }
    if (index < 0 || index > pArr->count) return false;
    for (int i = pArr->count - 1; i >= index; i--)
    {
        pArr->pBase[i+1] = pArr->pBase[i];
    }
    pArr->pBase[index] = val;
    ALOGI("insert %d at %d", val, index);
    pArr->count++;
    return true;
}

bool delete_arr(struct Array * pArr, int index)
{
    if (is_empty(pArr) || index < 0 || index > pArr->count -1) return false;
    ALOGI("delete %d", pArr->pBase[index]);
    for (int i = index; i < pArr->count; i++)
    {
        pArr->pBase[i] = pArr->pBase[index + 1];
    }
    pArr->count--;
    return true;
}

void reverse_arr(struct Array * pArr)
{
    for (int i = 0; i < (pArr->count) / 2; i++)
    {
        int tmp = pArr->pBase[i];
        pArr->pBase[i] = pArr->pBase[pArr->count - 1 - i];
        pArr->pBase[pArr->count - 1 - i] = tmp;
    }
}

// JNI call
#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT jlong JNICALL
Java_com_htr_test_utils_JNIUtil_initArray(JNIEnv *env, jclass clazz, jint length) {
    static struct Array array;// static is not temporary
    return init_arr(&array, length);
}

JNIEXPORT jboolean JNICALL
Java_com_htr_test_utils_JNIUtil_appendArray(JNIEnv *env, jclass clazz, jlong native_data, jint val) {
    return append_arr((struct Array*) native_data, val);
}

JNIEXPORT void JNICALL
Java_com_htr_test_utils_JNIUtil_showArray(JNIEnv *env, jclass clazz, jlong native_data) {
    show_arr((struct Array*) native_data);
}

JNIEXPORT jboolean JNICALL
Java_com_htr_test_utils_JNIUtil_insertArray(JNIEnv *env, jclass clazz, jlong native_data,
                                            jint index, jint val) {
    return insert_arr((struct Array*)native_data, index, val);
}

JNIEXPORT void JNICALL
Java_com_htr_test_utils_JNIUtil_reverseArray(JNIEnv *env, jclass clazz, jlong native_data) {
    reverse_arr((struct Array*)native_data);
}

JNIEXPORT jboolean JNICALL
Java_com_htr_test_utils_JNIUtil_deleteArray(JNIEnv *env, jclass clazz, jlong native_data, jint index)
{
    return delete_arr((struct Array*)native_data, index);
}
#ifdef __cplusplus
}
#endif
