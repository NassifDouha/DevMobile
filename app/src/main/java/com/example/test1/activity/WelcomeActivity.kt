package com.example.test1.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.test1.R

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Trouver le bouton
        val startButton = findViewById<Button>(R.id.startButton)

        // Ajouter un listener pour le clic du bouton
        startButton.setOnClickListener {
            // Créer une nouvelle intention pour ouvrir SecondActivity
            val intent = Intent(this, ToutesRecettes ::class.java)

            // Démarrer la nouvelle activité
            startActivity(intent)
        }
    }
}
