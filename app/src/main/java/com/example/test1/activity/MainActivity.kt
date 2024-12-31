package com.example.test1.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.test1.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Lancer WelcomeActivity après un délai de 2 secondes
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, WelcomeActivity::class.java)) // Démarrer WelcomeActivity
            finish() // Fermer MainActivity
        }, 2000) // 2000 millisecondes = 2 secondes
    }
}
