package com.robelseyoum3.cleancode.util

import android.util.Log
import com.robelseyoum3.cleancode.util.Constants.DEBUG
import com.robelseyoum3.cleancode.util.Constants.TAG

var isUnitTest = false

fun printLogD(className: String?, message: String ) {
    if (DEBUG && !isUnitTest) {
        Log.d(TAG, "$className: $message")
    }
    else if(DEBUG && isUnitTest){
        println("$className: $message")
    }
}

