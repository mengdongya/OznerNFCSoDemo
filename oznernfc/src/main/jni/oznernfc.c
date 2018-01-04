#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_ozner_nfc_OznerNfcManager_getKey(JNIEnv *env, jobject instance, jobject context) {

    // TODO


    return (*env)->NewStringUTF(env, returnValue);
}

extern "C" {
jstring test() {
    return "lingchen";
}

JNIEXPORT jstring JNICALL
Java_com_ozner_nfc_OznerNFC_getKey(JNIEnv *env, jobject instance, jobject context) {

    // TODO


    return (*env)->NewStringUTF(env, test());
}
}