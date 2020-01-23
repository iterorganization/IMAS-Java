#include <jni.h>
#include "ual_lowlevel.h"
#include "ual_const.h"



typedef std::complex < double > std_complex_t;

static bool isErrorCritical(int errorCode)
{  
    if (errorCode > -1)
        return false;
    
    if (errorCode == -5)
        return false;

   return true;
 }

static void raiseLowLevelException(JNIEnv *env, al_status_t alStatus)
{
    char msgBuffer[MAX_ERR_MSG_LEN + 20] = {0}; 
    bool isCritical = isErrorCritical(alStatus.code);

    if (!isCritical)
       return;

    sprintf(msgBuffer, "ERROR[%d]\n%s\n", alStatus.code, alStatus.message);

    jclass exc = env->FindClass("imasjava/UALException");
    env->ThrowNew(exc, msgBuffer);
 }


static void raiseException(JNIEnv *env, const char* callerName, const char* msg)
{
    jclass exc = env->FindClass("imasjava/UALException");
    env->ThrowNew(exc, msg);
 }

static std_complex_t convertToCppComplex(JNIEnv * env, jobject jComplex)
{
    std_complex_t cppComplex;
    jclass jComplexClass = env->FindClass("imasjava/Complex" );
    if(jComplexClass == NULL)
    {
        raiseException(env, "", "No class found: imasjava.Complex");
    } 
    
    jmethodID jmGetReal = env->GetMethodID(jComplexClass, "getReal","()D");
    if(jmGetReal == NULL)
    {
        raiseException(env, "", "No method found: getReal() of imasjava.Complex");
    } 
    jdouble jdReal = env->CallDoubleMethod(jComplex, jmGetReal);
    
    
    jmethodID jmGetImaginary = env->GetMethodID(jComplexClass, "getImaginary","()D");
    if(jmGetImaginary == NULL)
    {
        raiseException(env, "", "No method found: getImaginary() of imasjava.Complex");
    } 
    jdouble jdImaginary = env->CallDoubleMethod(jComplex, jmGetImaginary);
    
    cppComplex = std_complex_t (jdReal, jdImaginary);
    
    return cppComplex;
}


static jobject convertToJavaComplex(JNIEnv * env, std_complex_t cppComplex)
{
    jclass jComplexClass = env->FindClass("imasjava/Complex" );
    if(jComplexClass == NULL)
    {
        raiseException(env, "", "No class found: Complex");
    } 
    
    jmethodID jmConstructor = env->GetMethodID(jComplexClass, "<init>", "(DD)V");
    if(jmConstructor == NULL)
    {
        raiseException(env, "", "No constructor found for Complex");
    } 
    
    jobject newObj = env->NewObject(jComplexClass, jmConstructor, cppComplex.real(), cppComplex.imag());
    
    
    return newObj;
}

static std_complex_t* convertToCppComplexArray(JNIEnv * env, jobjectArray jComplexArray)
{
    jobject jComplex;
    std_complex_t cppComplex;

        
    jint sizeOfArray =  env->GetArrayLength(jComplexArray);
    
    std_complex_t* cppComplexArray = new std_complex_t[sizeOfArray];
    
    for(int i = 0; i < sizeOfArray; ++i )
    {
        jComplex = env->GetObjectArrayElement( jComplexArray, i );
        
        cppComplex = convertToCppComplex(env, jComplex);
    
        cppComplexArray[i] = cppComplex;
    }
    
    return cppComplexArray;
}

static jobjectArray convertToJavaComplexArray(JNIEnv * env, int iArraySize, std_complex_t* cppComplexArray)
{
    std_complex_t cppComplex;
    
    jclass jComplexClass = env->FindClass("imasjava/Complex" );
    if(jComplexClass == NULL)
    {
        raiseException(env, "", "No class found: Complex");
    } 
        
    jobjectArray javaArray;
    
    javaArray = env->NewObjectArray(iArraySize, jComplexClass, NULL);
    if (javaArray == NULL) 
    {
        raiseException(env, "", "Create javaArray failed.");
    }
                     

    for (int i = 0; i < iArraySize; i++) 
    {
        cppComplex = cppComplexArray[i];
        jobject newObj = convertToJavaComplex(env, cppComplex);
    
        env->SetObjectArrayElement(javaArray, i, newObj);
    }

    
    return javaArray;

}


/*
 * Class:     imasjava_wrapper_LowLevel
 * Method:    ual_print_context
 * Signature: (I)I
 */
 jstring JNICALL Java_imasjava_wrapper_LowLevel_ual_1context_1info
  (JNIEnv *env, jclass jWrapperClass, jint jCtx)
{
    al_status_t al_status;
    char *info;
    jstring jInfo;

    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_context_info((int)jCtx, &info);
    if (al_status.code < 0)
        raiseLowLevelException( env, al_status);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -

    if(info != NULL)
        jInfo = env->NewStringUTF(info);
    else
        jInfo = env->NewStringUTF("");

    return jInfo;
}
extern "C" {
/*
 * Class:     imasjava_wrapper_LowLevel
 * Method:    ual_begin_pulse_action
 * Signature: (IIILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
 * Signature: (IIILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
 */
 JNIEXPORT jint JNICALL Java_imasjava_wrapper_LowLevel_ual_1begin_1pulse_1action
  (JNIEnv *env, jclass jWrapperClass, jint jBackendId, jint jShot, jint jRun, jstring jUser, jstring jTokamak, jstring jVersion)
{
    al_status_t al_status;
    int ctx = -1; 

    const char* user = env->GetStringUTFChars(jUser, 0);
    const char* tokamak = env->GetStringUTFChars(jTokamak, 0);
    const char* version = env->GetStringUTFChars(jVersion, 0);

 

    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_begin_pulse_action(jBackendId, (int)jShot, (int)jRun, user, tokamak, version, &ctx);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -


    env->ReleaseStringUTFChars(jUser, user);
    env->ReleaseStringUTFChars(jTokamak, tokamak);
    env->ReleaseStringUTFChars(jVersion, version);

    if (al_status.code < 0)
        raiseLowLevelException( env, al_status);

    return ctx;
}

/*
 * Class:     imasjava_wrapper_LowLevel
 * Method:    ual_open_pulse
 * Signature: (IILjava/lang/String;)I
 */
 void JNICALL Java_imasjava_wrapper_LowLevel_ual_1open_1pulse
  (JNIEnv *env, jclass jWrapperClass, jint jCtx, jint jMode, jstring jOptions)
{
    al_status_t al_status;
    const char *options = env->GetStringUTFChars(jOptions, 0);

    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_open_pulse((int)jCtx, (int)jMode, options);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -

    env->ReleaseStringUTFChars(jOptions, options);

    if (al_status.code < 0)
        raiseLowLevelException( env, al_status);
}
/*
 * Class:     imasjava_wrapper_LowLevel
 * Method:    ual_close_pulse
 * Signature: (IILjava/lang/String;)I
 */
 void JNICALL Java_imasjava_wrapper_LowLevel_ual_1close_1pulse
  (JNIEnv *env, jclass jWrapperClass, jint jCtx, jint jMode, jstring jOptions)
{
    al_status_t al_status;
    const char *options = env->GetStringUTFChars(jOptions, 0);

    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_close_pulse((int)jCtx, (int)jMode, options);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -

    env->ReleaseStringUTFChars(jOptions, options);
   
    if (al_status.code < 0)
        raiseLowLevelException( env, al_status);
}
/*
 * Class:     imasjava_wrapper_LowLevel
 * Method:    ual_begin_global_action
 * Signature: (ILjava/lang/String;I)I
 */
 jint JNICALL Java_imasjava_wrapper_LowLevel_ual_1begin_1global_1action
  (JNIEnv *env, jclass jWrapperClass, jint jCtx, jstring jDataObjectName, jint jRWMode)
{
    al_status_t al_status;
    int ctx = -1; 
    const char *dataObjectName = env->GetStringUTFChars(jDataObjectName, 0);


    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_begin_global_action((int)jCtx, dataObjectName, (int)jRWMode, &ctx);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -


    env->ReleaseStringUTFChars(jDataObjectName, dataObjectName);

    if (al_status.code < 0)
        raiseLowLevelException( env, al_status);

    return ctx;
}
/*
 * Class:     imasjava_wrapper_LowLevel
 * Method:    ual_begin_slice_action
 * Signature: (ILjava/lang/String;IDI)I
 */
 jint JNICALL Java_imasjava_wrapper_LowLevel_ual_1begin_1slice_1action
  (JNIEnv *env, jclass jWrapperClass, jint jCtx,  jstring jDataObjectName, jint jRWMode, jdouble jTime, jint jInterpMode)
{
    al_status_t al_status;
    int ctx = -1; 
    const char *dataObjectName = env->GetStringUTFChars(jDataObjectName, 0);


    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_begin_slice_action((int)jCtx, dataObjectName, (int)jRWMode, (double)jTime, (int)jInterpMode, &ctx);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -


    env->ReleaseStringUTFChars(jDataObjectName, dataObjectName);

    if (al_status.code < 0)
        raiseLowLevelException( env, al_status);

    return ctx;
}
/*
 * Class:     imasjava_wrapper_LowLevel
 * Method:    ual_end_action
 * Signature: (I)I
 */
 void JNICALL Java_imasjava_wrapper_LowLevel_ual_1end_1action
  (JNIEnv *env, jclass jWrapperClass, jint jCtx)
{
    al_status_t al_status;

    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_end_action((int)jCtx);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -

    if (al_status.code < 0)
        raiseLowLevelException( env, al_status);
}

/*
 * Class:     imasjava_wrapper_LowLevel
 * Method:    ual_write_data_int
 * Signature: (ILjava/lang/String;Ljava/lang/String;[II[I)I
 */
 void JNICALL Java_imasjava_wrapper_LowLevel_ual_1write_1data_1int
  (JNIEnv *env, jclass jWrapperClass, jint jCtx, jstring jFieldPath, jstring jTimeBasePath, jintArray jData, jint jDim, jintArray jSizeArray)
{
    al_status_t al_status;

    const char *fieldPath = env->GetStringUTFChars(jFieldPath, 0);
    const char *timeBasePath = env->GetStringUTFChars(jTimeBasePath, 0);
    jint *dataArray = NULL;
    jint *sizeArray = NULL;

   //    jsize  len = env->GetArrayLength(jData);
    if(jData != NULL)
        dataArray = env->GetIntArrayElements(jData, 0);

    if(jSizeArray != NULL)
        sizeArray = env->GetIntArrayElements(jSizeArray, 0);

    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_write_data((int)jCtx, fieldPath, timeBasePath, (void*) dataArray, INTEGER_DATA, (int)jDim, (int*)sizeArray);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -

    env->ReleaseStringUTFChars(jFieldPath, fieldPath);
    env->ReleaseStringUTFChars(jTimeBasePath, timeBasePath);

    if(dataArray != NULL)
        env->ReleaseIntArrayElements(jData, dataArray, 0);

    if(sizeArray != NULL)
        env->ReleaseIntArrayElements(jSizeArray, sizeArray, 0);

    if (al_status.code < 0)
        raiseLowLevelException( env, al_status);
}

/*
 * Class:     imasjava_wrapper_LowLevel
 * Method:    ual_write_data_double
 * Signature: (ILjava/lang/String;Ljava/lang/String;[DI[I)I
 */
 void JNICALL Java_imasjava_wrapper_LowLevel_ual_1write_1data_1double
  (JNIEnv *env, jclass jWrapperClass, jint jCtx, jstring jFieldPath, jstring jTimeBasePath, jdoubleArray jData, jint jDim, jintArray jSizeArray)
{
    al_status_t al_status;

    const char *fieldPath = env->GetStringUTFChars(jFieldPath, 0);
    const char *timeBasePath = env->GetStringUTFChars(jTimeBasePath, 0);
    jdouble *dataArray = NULL;
    jint *sizeArray = NULL;

    if(jData != NULL)
        dataArray = env->GetDoubleArrayElements(jData, 0);

    if(jSizeArray != NULL)
        sizeArray = env->GetIntArrayElements(jSizeArray, 0);

    


    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_write_data((int)jCtx, fieldPath, timeBasePath, (void*) dataArray, DOUBLE_DATA, (int)jDim, (int*)sizeArray);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -

    env->ReleaseStringUTFChars(jFieldPath, fieldPath);
    env->ReleaseStringUTFChars(jTimeBasePath, timeBasePath);

    if(dataArray != NULL)
        env->ReleaseDoubleArrayElements(jData, dataArray, 0);

    if(sizeArray != NULL)
        env->ReleaseIntArrayElements(jSizeArray, sizeArray, 0);

    if (al_status.code < 0)
        raiseLowLevelException( env, al_status);
}

/*
 * Class:     imasjava_wrapper_LowLevel
 * Method:    ual_write_data_complex
 * Signature: (ILjava/lang/String;Ljava/lang/String;[Limasjava/Complex;I[I)V
 */
JNIEXPORT void JNICALL Java_imasjava_wrapper_LowLevel_ual_1write_1data_1complex
  (JNIEnv *env, jclass jWrapperClass, jint jCtx, jstring jFieldPath, jstring jTimeBasePath, jobjectArray jComplexArray, jint jDim, jintArray jSizeArray)
{
    al_status_t al_status;

    const char *fieldPath = env->GetStringUTFChars(jFieldPath, 0);
    const char *timeBasePath = env->GetStringUTFChars(jTimeBasePath, 0);
    std_complex_t *cppDataArray = NULL; 
    jint *sizeArray = NULL;

    if(jComplexArray != NULL)
        cppDataArray = convertToCppComplexArray(env, jComplexArray);

    if(jSizeArray != NULL)
        sizeArray = env->GetIntArrayElements(jSizeArray, 0);

    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_write_data((int)jCtx, fieldPath, timeBasePath, (void*) cppDataArray, COMPLEX_DATA, (int)jDim, (int*)sizeArray);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -

    env->ReleaseStringUTFChars(jFieldPath, fieldPath);
    env->ReleaseStringUTFChars(jTimeBasePath, timeBasePath);

    if(cppDataArray != NULL)
       delete cppDataArray;

    if(sizeArray != NULL)
        env->ReleaseIntArrayElements(jSizeArray, sizeArray, 0);

    if (al_status.code < 0)
        raiseLowLevelException( env, al_status);
}


/*
 * Class:     imasjava_wrapper_LowLevel
 * Method:    ual_write_data_char
 * Signature: (ILjava/lang/String;Ljava/lang/String;[BI[I)I
 */
 void JNICALL Java_imasjava_wrapper_LowLevel_ual_1write_1data_1char
  (JNIEnv *env, jclass jWrapperClass, jint jCtx, jstring jFieldPath, jstring jTimeBasePath, jbyteArray jData, jint jDim, jintArray jSizeArray)
{
    al_status_t al_status;

    const char *fieldPath = env->GetStringUTFChars(jFieldPath, 0);
    const char *timeBasePath = env->GetStringUTFChars(jTimeBasePath, 0);

    jint *sizeArray = NULL;
    jbyte *dataArray = NULL;


   if(jData != NULL)
        dataArray = env->GetByteArrayElements(jData, 0);

   if(jSizeArray != NULL)
        sizeArray = env->GetIntArrayElements(jSizeArray, 0);




    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_write_data((int)jCtx, fieldPath, timeBasePath, dataArray, CHAR_DATA, (int)jDim, (int*)sizeArray);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -

    env->ReleaseStringUTFChars(jFieldPath, fieldPath);
    env->ReleaseStringUTFChars(jTimeBasePath, timeBasePath);

    if(dataArray != NULL)
        env->ReleaseByteArrayElements(jData, dataArray, 0);

    if(sizeArray != NULL)
        env->ReleaseIntArrayElements(jSizeArray, sizeArray, 0);

    if (al_status.code < 0)
        raiseLowLevelException( env, al_status);
}
/*
 * Class:     imasjava_wrapper_LowLevel
 * Method:    ual_read_data_int
 * Signature: (ILjava/lang/String;Ljava/lang/String;[II[I)I
 */
 jintArray JNICALL Java_imasjava_wrapper_LowLevel_ual_1read_1data_1int
  (JNIEnv *env, jclass jWrapperClass, jint jCtx, jstring jFieldPath, jstring jTimeBasePath, jint jDim, jintArray jSizeArray)
{
    al_status_t al_status;
    jsize retArraySize = 1;
    jintArray jData = NULL;
    const char *fieldPath = env->GetStringUTFChars(jFieldPath, 0);
    const char *timeBasePath = env->GetStringUTFChars(jTimeBasePath, 0);
    jint *dataArray = NULL;
    jint tmpScalar = 0;
    //jsize len = env->GetArrayLength(jSizeArray);
    jint *sizeArray = env->GetIntArrayElements(jSizeArray, 0);


    if(jDim == 0)
    {
        dataArray = &tmpScalar;
    }


    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_read_data((int)jCtx, fieldPath, timeBasePath, (void**)&dataArray, INTEGER_DATA, (int)jDim, (int*)sizeArray);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -

    for (int i = 0; i < jDim; i++)
         retArraySize = retArraySize * sizeArray[i];

    jData = env->NewIntArray(retArraySize);
    if (jData == NULL) {
        raiseException( env, "Wrapper", "Out of memory error");
        return NULL; /* out of memory error thrown */
    }
    env->SetIntArrayRegion(jData, 0, retArraySize, dataArray);

    env->ReleaseStringUTFChars(jFieldPath, fieldPath);
    env->ReleaseStringUTFChars(jTimeBasePath, timeBasePath);
    env->ReleaseIntArrayElements(jSizeArray, sizeArray, 0);

    if (al_status.code < 0)
        raiseLowLevelException( env, al_status);

    return jData;
}
/*
 * Class:     imasjava_wrapper_LowLevel
 * Method:    ual_read_data_double
 * Signature: (ILjava/lang/String;Ljava/lang/String;[DI[I)I
 */
 jdoubleArray JNICALL Java_imasjava_wrapper_LowLevel_ual_1read_1data_1double
  (JNIEnv *env, jclass jWrapperClass, jint jCtx, jstring jFieldPath, jstring jTimeBasePath, jint jDim, jintArray jSizeArray)
{
    al_status_t al_status;
    jsize retArraySize = 1;
    jdoubleArray jData = NULL;
    const char *fieldPath = env->GetStringUTFChars(jFieldPath, 0);
    const char *timeBasePath = env->GetStringUTFChars(jTimeBasePath, 0);
    jdouble *dataArray = NULL;
    jdouble tmpScalar = 0;
    jint *sizeArray = env->GetIntArrayElements(jSizeArray, 0);

    if(jDim == 0)
    {
        dataArray = &tmpScalar;
    }

    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_read_data((int)jCtx, fieldPath, timeBasePath, (void**)&dataArray, DOUBLE_DATA, (int)jDim, (int*)sizeArray);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -


    for (int i = 0; i < jDim; i++)
       retArraySize = retArraySize * sizeArray[i];



    jData = env->NewDoubleArray(retArraySize);
    if (jData == NULL) {
     return NULL; /* out of memory error thrown */
    }
    env->SetDoubleArrayRegion(jData, 0, retArraySize, dataArray);
    env->ReleaseStringUTFChars(jFieldPath, fieldPath);
    
    env->ReleaseStringUTFChars(jTimeBasePath, timeBasePath);
    env->ReleaseIntArrayElements(jSizeArray, sizeArray, 0);

    if (al_status.code < 0)
        raiseLowLevelException( env, al_status);

    return jData;
}

/*
 * Class:     imasjava_wrapper_LowLevel
 * Method:    ual_read_data_complex
 * Signature: (ILjava/lang/String;Ljava/lang/String;I[I)[Limasjava/Complex;
 */
JNIEXPORT jobjectArray JNICALL Java_imasjava_wrapper_LowLevel_ual_1read_1data_1complex
  (JNIEnv *env, jclass jWrapperClass, jint jCtx, jstring jFieldPath, jstring jTimeBasePath, jint jDim, jintArray jSizeArray)
{
    al_status_t al_status;
    jsize retArraySize = 1;
    jobjectArray jData = NULL;
    const char *fieldPath = env->GetStringUTFChars(jFieldPath, 0);
    const char *timeBasePath = env->GetStringUTFChars(jTimeBasePath, 0);
    std_complex_t *cppDataArray = NULL;
    std_complex_t tmpScalar = 0;
    jint *sizeArray = env->GetIntArrayElements(jSizeArray, 0);

    if(jDim == 0)
        cppDataArray = &tmpScalar;
   
    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_read_data((int)jCtx, fieldPath, timeBasePath, (void**)&cppDataArray, COMPLEX_DATA, (int)jDim, (int*)sizeArray);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -

    for (int i = 0; i < jDim; i++)
       retArraySize = retArraySize * sizeArray[i];

    jData = convertToJavaComplexArray(env, retArraySize, cppDataArray);


    if(jDim > 0)
     delete cppDataArray;


    env->ReleaseStringUTFChars(jFieldPath, fieldPath);
    env->ReleaseStringUTFChars(jTimeBasePath, timeBasePath);
    env->ReleaseIntArrayElements(jSizeArray, sizeArray, 0);

    if (al_status.code < 0)
        raiseLowLevelException( env, al_status);

    return jData;
}

/*
 * Class:     imasjava_wrapper_LowLevel
 * Method:    ual_read_data_char
 * Signature: (ILjava/lang/String;Ljava/lang/String;[BI[I)I
 */
 jbyteArray JNICALL Java_imasjava_wrapper_LowLevel_ual_1read_1data_1char
  (JNIEnv *env, jclass jWrapperClass, jint jCtx, jstring jFieldPath, jstring jTimeBasePath, jint jDim, jintArray jSizeArray)
{
    al_status_t al_status;
    jsize retArraySize = 1;
    jbyteArray jData = NULL;
    const char *fieldPath = env->GetStringUTFChars(jFieldPath, 0);
    const char *timeBasePath = env->GetStringUTFChars(jTimeBasePath, 0);
    jbyte *dataArray =NULL;
    jbyte tmpScalar = 0;
    jint *sizeArray = env->GetIntArrayElements(jSizeArray, 0);

    if(jDim == 0)
    {
        dataArray = &tmpScalar;
    }

    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_read_data((int)jCtx, fieldPath, timeBasePath, (void**)&dataArray, CHAR_DATA, (int)jDim, (int*)sizeArray);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -


        for (int i = 0; i < jDim; i++)
            retArraySize = retArraySize * sizeArray[i];


    jData = env->NewByteArray(retArraySize);
    if (jData == NULL) {
     return NULL; /* out of memory error thrown */
    }

    env->SetByteArrayRegion(jData, 0, retArraySize, dataArray);

    env->ReleaseStringUTFChars(jFieldPath, fieldPath);
    env->ReleaseStringUTFChars(jTimeBasePath, timeBasePath);
    env->ReleaseIntArrayElements(jSizeArray, sizeArray, 0);

    if (al_status.code < 0)
        raiseLowLevelException( env, al_status);

    return jData;
}
/*
 * Class:     imasjava_wrapper_LowLevel
 * Method:    ual_delete_data
 * Signature: (ILjava/lang/String;)I
 */
 void JNICALL Java_imasjava_wrapper_LowLevel_ual_1delete_1data
  (JNIEnv *env, jclass jWrapperClass, jint jCtx, jstring jFieldPath)
{
    al_status_t al_status;

    const char *fieldPath = env->GetStringUTFChars(jFieldPath, 0);

    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_delete_data((int)jCtx, fieldPath);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -

    env->ReleaseStringUTFChars(jFieldPath, fieldPath);

    if (al_status.code < 0)
        raiseLowLevelException( env, al_status);
}
/*
 * Class:     imasjava_wrapper_LowLevel
 * Method:    ual_begin_arraystruct_action
 * Signature: (ILjava/lang/String;Ljava/lang/String;[I)I
 */
 jint JNICALL Java_imasjava_wrapper_LowLevel_ual_1begin_1arraystruct_1action
  (JNIEnv *env, jclass jWrapperClass, jint jCtx, jstring jFieldPath, jstring jTimeBasePath, jintArray jSizeArray)
{
    al_status_t al_status;
    int ctx = -1; 

    const char *fieldPath = env->GetStringUTFChars(jFieldPath, 0);
    const char *timeBasePath = env->GetStringUTFChars(jTimeBasePath, 0);
    jint *sizeArray = env->GetIntArrayElements(jSizeArray, 0);

    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_begin_arraystruct_action((int)jCtx, fieldPath, timeBasePath, (int*)sizeArray, &ctx);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -

    env->ReleaseStringUTFChars(jFieldPath, fieldPath);
    env->ReleaseStringUTFChars(jTimeBasePath, timeBasePath);
    env->ReleaseIntArrayElements(jSizeArray, sizeArray, 0);

    if (al_status.code < 0)
        raiseLowLevelException( env, al_status);

    return ctx;
}
/*
 * Class:     imasjava_wrapper_LowLevel
 * Method:    ual_iterate_over_arraystruct
 * Signature: (II)I
 */
 void JNICALL Java_imasjava_wrapper_LowLevel_ual_1iterate_1over_1arraystruct
  (JNIEnv *env, jclass jWrapperClass, jint jAoSCtx, jint jStep)
{
    al_status_t al_status;

    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_iterate_over_arraystruct((int)jAoSCtx, (int)jStep);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -


    if (al_status.code < 0)
        raiseLowLevelException( env, al_status);

}

int JNICALL Java_imasjava_wrapper_LowLevel_ual_1get_1backendID
  (JNIEnv *env, jclass jWrapperClass, jint backendID)
{   
    al_status_t al_status;
    int beid = -1;

    // - - - - - - - - - - UAL LowLevel method call - - - - - - - - - - - -
    al_status = ual_get_backendID(backendID, &beid);
    // - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - - - -

    if (al_status.code < 0)
      raiseLowLevelException( env, al_status );

    return beid;
}

} //extern "C"
