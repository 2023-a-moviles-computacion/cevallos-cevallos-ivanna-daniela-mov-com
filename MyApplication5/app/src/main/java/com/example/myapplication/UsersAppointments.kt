package com.example.myapplication

import android.content.Intent
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class UsersAppointments : AppCompatActivity(), RecyclerViewAdaptadorUsersAppointments.OnItemClickListener {
    private lateinit var recyclerViewUsersAppointment: RecyclerView
    private lateinit var appointmentAdapter: RecyclerViewAdaptadorUsersAppointments
    private val appointmentList = ArrayList<Appointment>()
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_appointments)

        val currentUserUid = auth.currentUser?.uid
        if (currentUserUid != null) {
            fetchAppointmentsForCurrentUser(currentUserUid)
        }
        val botonRegresar = findViewById<ImageButton>(R.id.imb_regresarus)
        botonRegresar.setOnClickListener {
            irActividad(SelectCategory::class.java)
        }

    }

    override fun onItemClick(){

    }
    private fun fetchAppointmentsForCurrentUser(userId: String) {
        // Query appointments where 'idPaciente' is equal to the current user's ID
        db.collection("appointments")
            .whereEqualTo("idPaciente", userId)
            .orderBy("DATE", Query.Direction.ASCENDING) // You can order the appointments as needed
            .get()
            .addOnSuccessListener { appointmentQuerySnapshot ->
                appointmentList.clear() // Clear the existing list
                for (appointmentDocument in appointmentQuerySnapshot.documents) {
                    // Map the document to an Appointment object (you need to define Appointment class)
                    val appointment = appointmentDocument.toObject(Appointment::class.java)
                    if (appointment != null) {
                        // Retrieve the date using the correct case for the field name
                        val dateFormatted = appointmentDocument.getString("DATE")
                        appointment.date = dateFormatted // Update the date field in Appointment
                        appointmentList.add(appointment)
                    }
                }

                displayRecyclerViewUsersAppointment()
                appointmentAdapter.notifyDataSetChanged() // Notify the adapter of data change
            }
            .addOnFailureListener { e ->
                showToast("Failed to fetch appointments: ${e.message}")
                Log.e("UsersAppointments", "Failed to fetch appointments", e)
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun displayRecyclerViewUsersAppointment(){
        recyclerViewUsersAppointment = findViewById(R.id.rv_usersAppointments)
        val layoutManagerDay = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewUsersAppointment.layoutManager = layoutManagerDay
        appointmentAdapter= RecyclerViewAdaptadorUsersAppointments(this, appointmentList)
        recyclerViewUsersAppointment .adapter = appointmentAdapter
    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

}