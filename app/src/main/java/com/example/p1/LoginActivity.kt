package com.example.p1

import LoginRequest
import LoginResponse
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val textViewForgotPassword = findViewById<TextView>(R.id.textViewForgotPassword)
        val textViewCreateAccount = findViewById<TextView>(R.id.textViewCreateAccount)

        // Login button click event
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Make the login request using Retrofit
                val loginRequest = LoginRequest(email, password)
                ApiClient.userApiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            val loginResponse = response.body()

                            if (loginResponse != null) {
                                val role = loginResponse.role
                                val token = loginResponse.token
                                val userId = loginResponse.id

                                // You can save the token for future use
                                // SharedPreferences can be used here to store the JWT token
                                // val sharedPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                                // sharedPrefs.edit().putString("authToken", token).apply()

                                // Navigate to the appropriate dashboard based on the role
                                if (role == "User") {
                                    val intent = Intent(this@LoginActivity, UserDashboardActivity::class.java)
                                    startActivity(intent)
                                } else if (role == "Provider") {
                                    val intent = Intent(this@LoginActivity, ProviderDashboardActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                        } else {
                            Toast.makeText(this@LoginActivity, "Login failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            }
        }


        // Forgot Password and Create Account events
        textViewForgotPassword.setOnClickListener {
            Toast.makeText(this, "Mot de passe oublié cliqué", Toast.LENGTH_SHORT).show()
        }

        textViewCreateAccount.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleLoginResponse(response: LoginResponse) {
        when (response.role) {
            "User" -> {
                Toast.makeText(this, "Bienvenue, ${response.username} (Utilisateur)", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, UserDashboardActivity::class.java)
                intent.putExtra("userData", response.data.toString()) // Pass user data if needed
                startActivity(intent)
            }
            "Provider" -> {
                Toast.makeText(this, "Bienvenue, ${response.username} (Prestataire)", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ProviderDashboardActivity::class.java)
                intent.putExtra("providerData", response.data.toString()) // Pass provider data if needed
                startActivity(intent)
            }
            else -> {
                Toast.makeText(this, "Rôle inconnu : ${response.role}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
