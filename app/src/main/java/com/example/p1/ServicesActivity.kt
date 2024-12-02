package com.example.p1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ServicesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)

        val recyclerView: RecyclerView = findViewById(R.id.servicesRecyclerView)
        val errorTextView: TextView = findViewById(R.id.errorTextView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val apiService = ApiClient.retrofit.create(ApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val services = apiService.getAllServices()
                withContext(Dispatchers.Main) {
                    recyclerView.adapter = ServicesAdapter(services)
                    recyclerView.visibility = View.VISIBLE
                    errorTextView.visibility = View.GONE
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    errorTextView.text = "Erreur lors du chargement des services : ${e.message}"
                    errorTextView.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Log.e("ServicesActivity", "Erreur : ${e.message}")
            }
        }
    }
}
