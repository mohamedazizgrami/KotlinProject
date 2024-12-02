package com.example.p1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProviderDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_dashboard)

        // Récupère les données de l'intent (si présentes)
        val providerData = intent.getStringExtra("providerData")

        // Exemple : Affiche les informations du prestataire
        val textViewWelcome = findViewById<TextView>(R.id.textViewWelcome)
        textViewWelcome.text = "Bienvenue, prestataire !\nDonnées : $providerData"
    }
}
