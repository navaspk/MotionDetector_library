package com.example.detector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bird.motiondetector.plugin.MotionDetector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        get_data.setOnClickListener {
            startActivity(Intent(this, DetailsActivity::class.java))
        }
    }
}
