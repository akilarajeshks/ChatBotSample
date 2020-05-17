package com.zestworks.woebotapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zestworks.woebotapplication.R
import kotlinx.serialization.UnstableDefault

@UnstableDefault
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}