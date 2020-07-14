package com.robelseyoum3.cleancode.framework.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.robelseyoum3.cleancode.R

class MainActivity : AppCompatActivity() {

    private val TAG: String = "AppDebug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
