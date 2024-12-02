package com.example.p1

data class Location(
    val _id: String,      // MongoDB's unique identifier
    val address: String,  // The address field
    val ville: String,    // The city or governorate (ville)
    val postal_code: String, // Postal code
    val country: String   // Country name
) {
    // Custom toString method to match the Mongoose schema's toString() functionality
    override fun toString(): String {
        return "$address, $ville, $country"
    }
}
