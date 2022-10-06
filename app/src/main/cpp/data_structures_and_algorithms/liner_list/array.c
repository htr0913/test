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

size_t init_arr(int length);
bool insert_arr(struct Array* pArr, int index, int val);
bool delete_arr(struct Array* pArr, int index, int *pVal);
void reverse_arr(struct Array* pArr);
bool is_empty(struct Array * pArr);
bool is_full(struct Array* pArr);
bool append_arr(struct Array * pArr, int val);
void show_arr(struct Array* pArr);

size_t init_arr(int length)
{
    /*
     * rules of memory allocation of the struct and its members?
     *
     * */
    struct Array *pArr = malloc(sizeof(struct Array) + sizeof(int) * length);
    if (pArr == NULL)
    {
        ALOGI("init array failed!");
        return NULL;
    }

    ALOGI("sizeof struct Array:%zu, sizeof *pArr:%zu, pArr:%p, pBase:%p",
         sizeof(struct Array), sizeof(*pArr), pArr, &(pArr->pBase));
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
    if (is_full(pArr)) ALOGI("empty array!");
    for (int i = 0; i < pArr->count; i++)
    {
        ALOGI("%d", pArr->pBase[i]);
    }
    ALOGI("\n");
}

bool append_arr(struct Array * pArr, int val)
{
    if(is_full(pArr)) return false;
    pArr->pBase[pArr->count] = val;
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
    pArr->count++;
    return true;
}

//ToDo: delete arr return the point of the element deleted
bool delete_arr(struct Array * pArr, int index, int * pVal)
{
    if (is_empty(pArr)) return false;
    if (index < 0 || index > pArr->count -1) return false;
    *pVal = pArr->pBase[index];
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

#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT jlong JNICALL
Java_com_htr_test_utils_JNIUtil_initArray(JNIEnv *env, jclass clazz, jint length) {
        return init_arr(length);
}
#ifdef __cplusplus
}
#endif
