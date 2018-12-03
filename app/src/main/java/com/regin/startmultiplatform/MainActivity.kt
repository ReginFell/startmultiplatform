package com.regin.startmultiplatform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.regin.com.regin.startmultiplatform.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shared.text = provideSharedData()
        platform.text = providePlatformData()
    }
}
