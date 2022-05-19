package com.example.videosu

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    lateinit var editTextName: TextInputEditText
    lateinit var editTextEmail: TextInputEditText
    lateinit var btnScreen3: Button

    lateinit var imageView: ImageView

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.userName)
        editTextEmail = findViewById(R.id.userEmail)
        imageView = findViewById(R.id.imageView)
        imageView.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            activityResultLauncher.launch(intent)
        }

        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
                if (result!!.resultCode == Activity.RESULT_OK) {
                    bitmap = result.data!!.extras!!.get("data") as Bitmap
                    imageView.setImageBitmap(bitmap)
                } else {
                    Toast.makeText(applicationContext, "Image not clicked", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        btnScreen3 = findViewById(R.id.btn_Screen3)
        btnScreen3.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val name = editTextName.text.toString().trim()

            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

            if (email.isEmpty() && name.isEmpty()) {
                Toast.makeText(applicationContext, "Field cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            } else if (!email.matches(emailPattern.toRegex())) {
                Toast.makeText(applicationContext, "Enter valid email id..!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val intent = Intent(this, Screen3::class.java)
                intent.putExtra("bitmap", bitmap)
                intent.putExtra("email", email)
                intent.putExtra("name", name)
                startActivity(intent)
            }
        }
    }
}