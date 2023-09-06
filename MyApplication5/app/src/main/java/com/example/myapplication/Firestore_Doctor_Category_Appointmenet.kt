package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class Firestore_Doctor_Category_Appointmenet : AppCompatActivity() {
    var query: Query? = null
    val arrayDoctors: ArrayList<Doctor> = arrayListOf()
    val arrayCategory: ArrayList<Category> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createCategories()
        createDoctors()
        createAppointments()
    }

    private fun createAppointments() {
        val db = Firebase.firestore
        val appointmentsCollection = db.collection("appointments")

        // Get the current date
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 9) // Set the hour to 9 AM
        calendar.set(Calendar.MINUTE, 0) // Set the minutes to 0
        var currentDate = calendar.time

        // Create appointments for the next 7 days
        for (i in 0 until 7) {
            // Get a list of all doctors from Firestore
            db.collection("doctors")
                .get()
                .addOnSuccessListener { doctorQuerySnapshot ->
                    for (doctorDocument in doctorQuerySnapshot.documents) {

                        // Create 10 appointments for each doctor on the current day
                        for (j in 0 until 5) {
                            // Calculate the appointment time
                            calendar.time = currentDate
                            calendar.add(Calendar.MINUTE, j * 30) // Add 30 minutes for each appointment
                            val appointmentTime = calendar.time

                            // Generate a random appointment ID
                            val randomId = (1000000000..9999999999).random()
                            val appointmentId = randomId.toString()

                            // Format the date, hour, and minutes
                            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            val dateFormatted = dateFormat.format(appointmentTime)

                            val hourFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                            val hourFormatted = hourFormat.format(appointmentTime)

                            // Create the appointment data
                            val appointmentData = hashMapOf(
                                "DATE" to dateFormatted, // Store the formatted date
                                "hour" to hourFormatted, // Store the formatted hour
                                "idPaciente" to "",
                                "idMedico" to doctorDocument.id
                            )

                            // Add the appointment to the Firestore collection
                            appointmentsCollection.document(appointmentId)
                                .set(appointmentData)
                                .addOnSuccessListener {
                                    // Handle success if needed
                                }
                                .addOnFailureListener { e ->
                                    // Handle the failure here
                                    showToast("Failed to add appointment: ${e.message}")
                                }
                        }
                    }

                    // Move to the next day
                    calendar.time = currentDate
                    calendar.add(Calendar.DAY_OF_YEAR, 1)
                    currentDate = calendar.time
                }
                .addOnFailureListener { e ->
                    // Handle the failure to retrieve doctors
                    showToast("Failed to retrieve doctors: ${e.message}")
                }
        }
    }

    private fun createDoctors() {
        val db = Firebase.firestore
        val referDoctor = db.collection("doctors")

        val doctorsData = arrayOf(
            hashMapOf("name" to "Jorge Ruiz", "specialityID" to 1),
            hashMapOf("name" to "Angelita Camacho", "specialityID" to 1),
            hashMapOf("name" to "Thomas Quiroga", "specialityID" to 2),
            hashMapOf("name" to "Alex Jaramillo", "specialityID" to 3),
            hashMapOf("name" to "Abigail Tapia", "specialityID" to 4),
            hashMapOf("name" to "Gustavo Andrade", "specialityID" to 5)
        )

        val batch = db.batch()

        for (doctorData in doctorsData) {
            val randomId = (1000000000..9999999999).random()
            val documentId = randomId.toString()
            val doctorRef = referDoctor.document(documentId)
            batch.set(doctorRef, doctorData)
        }

        // Commit the batch of set operations
        batch.commit()
            .addOnSuccessListener {
                // All documents have been successfully added
                startMainActivity()
            }
            .addOnFailureListener { e ->
                // Handle the failure here
                showToast("Failed to add doctors: ${e.message}")
            }
    }
    private fun createCategories() {
        val db = Firebase.firestore
        val refCategory = db.collection("categories")
        val categories = listOf("Cardiology", "Neurology", "Dermatologists", "Otology", "Gastroenterologists")
        val images = listOf("cardio", "neuro", "derma", "orto", "gastro")

        for ((index, category) in categories.withIndex()) {
            val dataCategory = hashMapOf(
                "name" to category,
                "imageID" to images[index]
            )
            refCategory
                .document(index.toString()) // Use index as the document ID
                .set(dataCategory)
                .addOnSuccessListener {
                    // Handle success if needed
                }
                .addOnFailureListener {
                    // Handle failure if needed
                }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}