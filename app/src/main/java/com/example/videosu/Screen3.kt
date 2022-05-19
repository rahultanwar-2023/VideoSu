package com.example.videosu

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class Screen3 : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var textView: TextView
    lateinit var textView1: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen3)

        imageView = findViewById(R.id.profilePic)
        textView = findViewById(R.id.showEmail)
        textView1 = findViewById(R.id.showName)

        val bitmap = intent.getParcelableExtra<Bitmap>("bitmap")
        val email = intent.getStringExtra("email")
        val name = intent.getStringExtra("name")

        imageView.setImageBitmap(bitmap)
        textView.text = email
        textView1.text = name

    }
}