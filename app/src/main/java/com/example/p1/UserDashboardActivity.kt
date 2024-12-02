package com.example.p1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UserDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_dashboard)

        // Récupère les données de l'intent (si présentes)
        val userData = intent.getStringExtra("userData")

        // Exemple : Affiche les informations de l'utilisateur
        val textViewWelcome = findViewById<TextView>(R.id.textViewWelcome)
        textViewWelcome.text = "Bienvenue, utilisateur !\nDonnées : $userData"
    }
}
