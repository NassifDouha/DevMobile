package com.example.test1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, RecipDetailsActivity::class.java))
            finish() // Finish MainActivity to prevent going back to it
        }, 2000) // 2000 milliseconds = 2 seconds
    }
}