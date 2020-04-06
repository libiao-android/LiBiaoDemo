#include "com_libiao_libiaodemo_jni_demo_JNITest.h"
#include <string>
#include <android/log.h>

#define  LOG_TAG    "libiao"
#define  ALOG(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

extern "C" {
#include "libavformat/avformat.h"
#include "libavcodec/avcodec.h"
#include "libavutil/avutil.h"
#include "libavfilter/avfilter.h"
#include "ffmpeg.h"
}

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

JNIEXPORT jstring JNICALL
Java_com_libiao_libiaodemo_jni_demo_JNITest_avformatInfo( JNIEnv* env, jobject /* this */) {
    char info[40000] = { 0 };
    av_register_all();
    AVInputFormat *if_temp = av_iformat_next(NULL);
    AVOutputFormat *of_temp = av_oformat_next(NULL);
    //Input
    while (if_temp != NULL){
        sprintf(info, "%s[In ][%10s]\n", info, if_temp->name);
        if_temp = if_temp->next;
    }
    //Output
    while (of_temp != NULL) {
        sprintf(info, "%s[Out][%10s]\n", info, of_temp->name);
        of_temp = of_temp->next;
    }

    return env->NewStringUTF(info);
}

JNIEXPORT jstring JNICALL
Java_com_libiao_libiaodemo_jni_demo_JNITest_avcodecInfo( JNIEnv* env, jobject /* this */) {
    char info[40000] = { 0 };

    av_register_all();

    AVCodec *c_temp = av_codec_next(NULL);

    while (c_temp!=NULL) {
        if (c_temp->decode!=NULL){
            sprintf(info, "%s[Dec]", info);
        }
        else{
            sprintf(info, "%s[Enc]", info);
        }
        switch (c_temp->type) {
            case AVMEDIA_TYPE_VIDEO:
                sprintf(info, "%s[Video]", info);
                break;
            case AVMEDIA_TYPE_AUDIO:
                sprintf(info, "%s[Audio]", info);
                break;
            default:
                sprintf(info, "%s[Other]", info);
                break;
        }
        sprintf(info, "%s[%10s]\n", info, c_temp->name);


        c_temp=c_temp->next;
    }

    return env->NewStringUTF(info);
}

JNIEXPORT jstring JNICALL
Java_com_libiao_libiaodemo_jni_demo_JNITest_avfilterInfo( JNIEnv* env, jobject /* this */) {
    char info[40000] = { 0 };
    avfilter_register_all();
    AVFilter *f_temp = (AVFilter *)avfilter_next(NULL);
    while (f_temp != NULL){
        sprintf(info, "%s[%10s]\n", info, f_temp->name);
        f_temp = f_temp->next;
    }


    return env->NewStringUTF(info);
}

JNIEXPORT jstring JNICALL
Java_com_libiao_libiaodemo_jni_demo_JNITest_configurationInfo( JNIEnv* env, jobject /* this */) {
    char info[10000] = {0};

    sprintf(info, "%s\n", avcodec_configuration());


    return env->NewStringUTF(info);
}

JNIEXPORT jint JNICALL
Java_com_libiao_libiaodemo_jni_demo_JNITest_run( JNIEnv* env, jobject /* this */, jobjectArray commands) {
    int argc = env->GetArrayLength(commands);
    char *argv[argc];

    int i;
    for (i = 0; i < argc; i++) {
        jstring js = (jstring) env->GetObjectArrayElement(commands, i);
        argv[i] = (char*) (env)->GetStringUTFChars(js, 0);
    }
    return ffmpeg_exec(argc, argv);
}

JNIEXPORT jint JNICALL
Java_com_libiao_libiaodemo_jni_demo_JNITest_getProgress( JNIEnv* env, jobject /* this */) {

    return get_progress();
}

JNIEXPORT jbyteArray JNICALL
Java_com_libiao_libiaodemo_jni_demo_JNITest_audioMix(JNIEnv *env, jobject, jbyteArray sourceA_,
                                                           jbyteArray sourceB_, jbyteArray dst_, jfloat firstVol,
                                                           jfloat secondVol) {
    jbyte *sourceA = env->GetByteArrayElements(sourceA_, NULL);
    jbyte *sourceB = env->GetByteArrayElements(sourceB_, NULL);
    jbyte *dst = env->GetByteArrayElements(dst_, NULL);
    //归一化混音
    int aL = env->GetArrayLength(sourceA_);
    int bL = env->GetArrayLength(sourceB_);
    int row = aL / 2;
    short sA[row];
    for (int i = 0; i < row; ++i) {
        sA[i] = (short) ((sourceA[i * 2] & 0xff) | (sourceA[i * 2 + 1] & 0xff) << 8);
    }

    short sB[row];
    for (int i = 0; i < row; ++i) {
        sB[i] = (short) ((sourceB[i * 2] & 0xff) | (sourceB[i * 2 + 1] & 0xff) << 8);
    }
    short result[row];
    for (int i = 0; i < row; ++i) {
        int a = (int) (sA[i] * firstVol);
        int b = (int) (sB[i] * secondVol);
        if (a < 0 && b < 0){
            int  i1 = a + b - a * b / (-32768);
            if (i1 > 32768){
                result[i] =  32767;
            } else if (i1 < - 32768){
                result[i] = - 32768;
            } else{
                result[i] = (short) i1;
            }
        } else if (a > 0 && b > 0){
            int i1 = a + b - a  * b  / 32767;
            if (i1 > 32767){
                result[i] = 32767;
            }else if (i1 < - 32768){
                result[i] = -32768;
            }else {
                result[i] = (short) i1;
            }
        } else{
            int i1 = a + b ;
            if (i1 > 32767){
                result[i] = 32767;
            }else if (i1 < - 32768){
                result[i] = -32768;
            }else {
                result[i] = (short) i1;
            }
        }
    }
    for (int i = 0; i <row ; ++i) {
        dst[i * 2 + 1] = (jbyte) ((result[i] & 0xFF00) >> 8);
        dst[i * 2] = (jbyte) (result[i] & 0x00FF);
    }

    jbyteArray result1 = env ->NewByteArray(aL);
    env->SetByteArrayRegion(result1, 0, aL, dst);

    env->ReleaseByteArrayElements(sourceA_, sourceA, 0);
    env->ReleaseByteArrayElements(sourceB_, sourceB, 0);
    env->ReleaseByteArrayElements(dst_, dst, 0);

    return result1;
}




JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    ALOG("keyHook JNI_OnLoad");

    return JNI_VERSION_1_6;
}



