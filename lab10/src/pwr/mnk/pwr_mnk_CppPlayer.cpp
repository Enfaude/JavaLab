#include <jni.h>
#include "pwr_mnk_CppPlayer.h"
#include <stdlib.h>
#include <time.h>

JNIEXPORT jintArray JNICALL Java_pwr_mnk_CppPlayer_randomCpp (JNIEnv *env, jclass, jobjectArray) {
	srand (time(NULL));
	int x = rand() %5;
	int y = rand() %5;
	jintArray coords = env->NewIntArray(2);	
	jint coordsjint[] = {x, y};
	env->SetIntArrayRegion( coords, 0, 2, coordsjint );
	
	return coords;
}