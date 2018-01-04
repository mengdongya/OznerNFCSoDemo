//
// Created by ozner_67 on 2017/6/7.
//

#include <jni.h>
#include <string>
#include <stdio.h>
#include <string.h>
#include "math.h"

extern "C"{
/*
 * 发布APP签名，只有和本签名一直的app才会返回key
 * 这个RELEASE_SIGN的值是第一次运行程序时获取的值
 */

const char *OZNER_KEY = "717573";
const char *RELEASE_SIGN = "308202dd308201c5a00302010202040ab37d72300d06092a864886f70d01010b0500301f310e300c060355040b13056f7a6e6572310d300b060355040313046d656e67301e170d3137303732393036313032355a170d3432303732333036313032355a301f310e300c060355040b13056f7a6e6572310d300b060355040313046d656e6730820122300d06092a864886f70d01010105000382010f003082010a0282010100c13d4c45f1b2f50b184a28033f6fb6644fbe3123a4cec946874ebfd78c844d7256124b037f029a98ec7ec0744633b9cd3cf5eb02f1d6b091f8c91e79800a526a45db9ece6d60611b89b8dc478793d44050ec587ef5941770d8d28f98df5c95307cc9ea99c83dd32a40ece7a7dfbd31a79161f7d7109c20b1ca4a86d7ca5d5b4709075ae9064f6f3cda54e3fcef0604677b2d07db13e6c9d9dd35eff675de21bd09c814f05fbf7807bf9c17ffc65c1e6867c338c24170ca1d93198923b7f62d32f4e6de90ede4b15704543d012584316b994bcc60b7e16464058efb029898131c43926a65bd5cab95c7dc4e9689a2b4c793185e1e53e628966a5a523e0ef6ed8f0203010001a321301f301d0603551d0e041604140e43fc3c0167eea8d7c8197564f9418c94d5bf5a300d06092a864886f70d01010b05000382010100a8e56cb4e49c090805cbc81652bd0827360c0f69cd1073f31b5193962b3f878da16ac92e8dc7f5c436438e7f595633990a8491de46a2b0d96551590df69d31415f9b2c15ca1ecea3baaff100c64ce20f3b87266e174c89a111b23e32e8079ce1c71afde882c99a539a2ca28b63e5372301ac8fab74602640845fd2c2c72a0112d4de645357a70eba030fd29800a31fdadc90a697f82d25680f3a42773116bed6847e4e1b83268b3774f49f8827858d89b337947b1893f3c95bc60a25279687b4fa2a4f90d66902e2d0e5940935ec97d45593930888dc1c1df4ba318907929540ae7dd403ecf176bfdb7209fd2e41539164aa2a34cc8656579249c300a4770788";


JNIEXPORT jstring JNICALL
Java_com_ozner_nfc_OznerNfcManager_getKey(JNIEnv *env, jobject instance, jobject context) {


    jclass native_class = env->GetObjectClass(context);
    jmethodID pm_id = env->GetMethodID(native_class, "getPackageManager",
                                       "()Landroid/content/pm/PackageManager;");
    jobject pm_obj = env->CallObjectMethod(context, pm_id);
    jclass pm_clazz = env->GetObjectClass(pm_obj);
// 得到 getPackageInfo 方法的 ID
    jmethodID package_info_id = env->GetMethodID(pm_clazz, "getPackageInfo",
                                                 "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jclass native_classs = env->GetObjectClass(context);
    jmethodID mId = env->GetMethodID(native_classs, "getPackageName", "()Ljava/lang/String;");
    jstring pkg_str = static_cast<jstring>(env->CallObjectMethod(context, mId));
// 获得应用包的信息
    jobject pi_obj = env->CallObjectMethod(pm_obj, package_info_id, pkg_str, 64);
// 获得 PackageInfo 类
    jclass pi_clazz = env->GetObjectClass(pi_obj);
// 获得签名数组属性的 ID
    jfieldID signatures_fieldId = env->GetFieldID(pi_clazz, "signatures",
                                                  "[Landroid/content/pm/Signature;");
    jobject signatures_obj = env->GetObjectField(pi_obj, signatures_fieldId);
    jobjectArray signaturesArray = (jobjectArray) signatures_obj;
    jsize size = env->GetArrayLength(signaturesArray);
    jobject signature_obj = env->GetObjectArrayElement(signaturesArray, 0);
    jclass signature_clazz = env->GetObjectClass(signature_obj);
    jmethodID string_id = env->GetMethodID(signature_clazz, "toCharsString",
                                           "()Ljava/lang/String;");
    jstring str = static_cast<jstring>(env->CallObjectMethod(signature_obj, string_id));
    char *c_msg = (char *) env->GetStringUTFChars(str, 0);
    //return str;
    if (strcmp(c_msg, RELEASE_SIGN) == 0)//签名一致  返回合法的 api key，否则返回错误
    {
        return (env)->NewStringUTF(OZNER_KEY);
    } else {
        return (env)->NewStringUTF("error");
    }
}

JNIEXPORT jstring JNICALL
Java_com_ozner_nfc_OznerNFC_getKey(JNIEnv *env, jobject instance, jobject context) {


    jclass native_class = env->GetObjectClass(context);
    jmethodID pm_id = env->GetMethodID(native_class, "getPackageManager",
                                       "()Landroid/content/pm/PackageManager;");
    jobject pm_obj = env->CallObjectMethod(context, pm_id);
    jclass pm_clazz = env->GetObjectClass(pm_obj);
// 得到 getPackageInfo 方法的 ID
    jmethodID package_info_id = env->GetMethodID(pm_clazz, "getPackageInfo",
                                                 "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jclass native_classs = env->GetObjectClass(context);
    jmethodID mId = env->GetMethodID(native_classs, "getPackageName", "()Ljava/lang/String;");
    jstring pkg_str = static_cast<jstring>(env->CallObjectMethod(context, mId));
// 获得应用包的信息
    jobject pi_obj = env->CallObjectMethod(pm_obj, package_info_id, pkg_str, 64);
// 获得 PackageInfo 类
    jclass pi_clazz = env->GetObjectClass(pi_obj);
// 获得签名数组属性的 ID
    jfieldID signatures_fieldId = env->GetFieldID(pi_clazz, "signatures",
                                                  "[Landroid/content/pm/Signature;");
    jobject signatures_obj = env->GetObjectField(pi_obj, signatures_fieldId);
    jobjectArray signaturesArray = (jobjectArray) signatures_obj;
    jsize size = env->GetArrayLength(signaturesArray);
    jobject signature_obj = env->GetObjectArrayElement(signaturesArray, 0);
    jclass signature_clazz = env->GetObjectClass(signature_obj);
    jmethodID string_id = env->GetMethodID(signature_clazz, "toCharsString",
                                           "()Ljava/lang/String;");
    jstring str = static_cast<jstring>(env->CallObjectMethod(signature_obj, string_id));
    char *c_msg = (char *) env->GetStringUTFChars(str, 0);
    //return str;
    if (strcmp(c_msg, RELEASE_SIGN) == 0)//签名一致  返回合法的 api key，否则返回错误
    {
        return (env)->NewStringUTF(OZNER_KEY);
    } else {
        return (env)->NewStringUTF("error");
    }
}

}