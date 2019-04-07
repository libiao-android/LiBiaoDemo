#include "com_libiao_libiaodemo_jni_demo_JNITest.h"
#include <string>
#include <android/log.h>

#define  LOG_TAG    "libiao"
#define  ALOG(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

extern "C"

void fn() {
    static int a = 10; //静态局部变量存储于进程的全局数据区，即使函数返回，它的值也会保持不变。
    a++;
    ALOG("static value:%d", a);
}


JNIEXPORT jstring JNICALL
Java_com_libiao_libiaodemo_jni_demo_JNITest_getString( JNIEnv* env, jobject /* this */) {
    std::string hello = "Hello from C++";
    fn(); // ALOG static value:11
    fn(); // ALOG static value:12
    return env->NewStringUTF(hello.c_str());
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    ALOG("keyHook JNI_OnLoad");

    return JNI_VERSION_1_6;
}



