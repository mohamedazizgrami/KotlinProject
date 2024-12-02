package com.example.p1

import ApiClient
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var spinnerGovernorates: Spinner
    private lateinit var progressBar: ProgressBar
    private var selectedLocationId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val textViewLogin = findViewById<TextView>(R.id.textView2)
        textViewLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val editTextName: EditText = findViewById(R.id.editTextName)
        val editTextFirstName: EditText = findViewById(R.id.editTextFirstName)
        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val editTextPassword: EditText = findViewById(R.id.editTextPassword)
        spinnerGovernorates = findViewById(R.id.spinnerGovernorates)
        progressBar = findViewById(R.id.progressBar)

        // Initialize Spinner for Governorates dynamically from the database
        fetchGovernorates()

        // Sign Up Button Click
        val buttonCreateAccount: Button = findViewById(R.id.buttonCreateAccount)
        buttonCreateAccount.setOnClickListener {
            val name = editTextName.text.toString()
            val firstName = editTextFirstName.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            // Validate Fields
            if (name.isEmpty() || firstName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidEmail(email)) {
                Toast.makeText(this, "Email invalide", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedLocationId == null) {
                Toast.makeText(this, "Veuillez sélectionner un gouvernorat", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = ProgressBar.VISIBLE

            val user = User(
                id = null, // ID will be generated by the backend
                username = name,
                firstname = firstName,
                lastname = name,
                email = email,
                password = password,
                location = selectedLocationId // Pass the selected Location ID here
            )

            // Retrofit API call
            ApiClient.userApiService.createUser(user).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    progressBar.visibility = ProgressBar.GONE
                    if (response.isSuccessful) {
                        Toast.makeText(this@CreateAccountActivity, "Compte créé avec succès", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@CreateAccountActivity, LoginActivity::class.java))
                    } else {
                        val errorMessage = response.errorBody()?.string() ?: "Erreur inconnue"
                        Log.e("CreateAccount", "Error: ${response.code()} - $errorMessage")
                        Toast.makeText(this@CreateAccountActivity, "e$errorMessage ", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    progressBar.visibility = ProgressBar.GONE
                    Log.e("CreateAccount", "Failure: ${t.message}")
                    Toast.makeText(this@CreateAccountActivity, "Échec de la connexion au serveur", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    // Fetch Governorates from the server
    private fun fetchGovernorates() {
        progressBar.visibility = ProgressBar.VISIBLE

        ApiClient.locationApiService.getLocations().enqueue(object : Callback<List<Location>> {
            override fun onResponse(call: Call<List<Location>>, response: Response<List<Location>>) {
                progressBar.visibility = ProgressBar.GONE
                if (response.isSuccessful) {
                    val locations = response.body()
                    val governorates = locations?.map { it.toString() } ?: emptyList()

                    // Populate spinner with governorates (Location name)
                    val adapter = ArrayAdapter(this@CreateAccountActivity, android.R.layout.simple_spinner_dropdown_item, governorates)
                    spinnerGovernorates.adapter = adapter

                    // Save the Location ID in the background for future use
                    spinnerGovernorates.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parentView: AdapterView<*>, view: View?, position: Int, id: Long) {
                            // Get the selected location and save the Location ID
                            val selectedLocation = locations?.get(position)
                            if (selectedLocation != null) {
                                selectedLocationId = selectedLocation._id
                            } // Store the Location ID
                        }

                        override fun onNothingSelected(parentView: AdapterView<*>) {
                            // Handle case when nothing is selected
                        }
                    }
                } else {
                    Log.e("CreateAccount", "Failed to fetch locations: ${response.code()}")
                    Toast.makeText(this@CreateAccountActivity, "Erreur lors de la récupération des gouvernorats", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Location>>, t: Throwable) {
                progressBar.visibility = ProgressBar.GONE
                Log.e("CreateAccount", "Failure: ${t.message}")
                Toast.makeText(this@CreateAccountActivity, "Échec de la connexion au serveur", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Validate Email
    private fun isValidEmail(email: String): Boolean {
        val pattern = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")
        return pattern.matcher(email).matches()
    }
}