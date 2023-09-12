package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SelectAppointment : AppCompatActivity(), RecyclerViewAdaptadorDays.OnItemClickListener {

    private lateinit var recyclerViewDay: RecyclerView
    private lateinit var dayAdapter: RecyclerViewAdaptadorDays
    private val appointmentList = ArrayList<Appointment>()
    private var categoryName =""
    private lateinit var recyclerViewAppointment: RecyclerView
    private lateinit var appointmentAdapter: RecyclerViewAdaptadorAppointment
    private var daySelected = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_appointment)

        categoryName = intent.getStringExtra("categoryName")!!
        val categoryDisplay = findViewById<TextView>(R.id.txt_Category_Appoint)
        categoryDisplay.text = "Especiality : $categoryName"

        val dateList = generateConsecutiveDates()

        recyclerViewDay = findViewById(R.id.rv_days)
        val layoutManagerDay = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewDay.layoutManager = layoutManagerDay
        dayAdapter = RecyclerViewAdaptadorDays(this, dateList, this)
        recyclerViewDay.adapter = dayAdapter

        val botonRegresar = findViewById<ImageButton>(R.id.imb_regresarap)
        botonRegresar.setOnClickListener {
            irActividad(SelectCategory::class.java)
        }




    }

    override fun onItemClick(day: String) {
        daySelected = day
        Log.d("daySelected", "Days : $daySelected ")
        getAppointmentsForCategoryAndDay(categoryName!!,daySelected)

    }

    private fun generateConsecutiveDates(): List<String> {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateList = mutableListOf<String>()

        for (i in 0 until 7) {
            dateList.add(dateFormat.format(calendar.time))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return dateList
    }

    private fun getAppointmentsForCategoryAndDay(categoryName: String, date: String) {
        val db = Firebase.firestore

        // Step 1: Query doctors in the selected category
        db.collection("doctors")
            .whereEqualTo("specialityID", getCategoryID(categoryName))
            .get()
            .addOnSuccessListener { doctorQuerySnapshot ->
                val doctorIds = doctorQuerySnapshot.documents.map { it.id }

                // Step 2: Query appointments for the selected doctors and date
                db.collection("appointments")
                    .whereIn("idMedico", doctorIds)
                    .whereEqualTo("DATE", date)
                    .get()
                    .addOnSuccessListener { appointmentQuerySnapshot ->
                        // Clear the existing appointmentList
                        appointmentList.clear()

                        // Process the appointment data and add to the appointmentList
                        for (appointmentDocument in appointmentQuerySnapshot.documents) {
                            val id = appointmentDocument.id
                            val appointmentData = appointmentDocument.data
                            val date = appointmentData?.get("DATE") as String
                            val hour = appointmentData["hour"] as String
                            val idPaciente = appointmentData["idPaciente"] as String
                            val idMedico = appointmentData["idMedico"] as String
                            val appointment = Appointment(id, date, hour, idPaciente, idMedico)
                            if (idPaciente.isEmpty()) {
                                //val idMedico = appointmentData["idMedico"] as String
                                //val appointment = Appointment(id, date, hour, idPaciente, idMedico)
                                appointmentList.add(appointment)
                                Log.d("AppointmentID", "Appointment ID: $id")
                            }
                            displayRecyclerViewAppointment()
                        }

                        // Sort the appointmentList by appointment hour in descending order
                        appointmentList.sortBy{ it.hour }

                        // Notify the adapter of the data change
                        displayRecyclerViewAppointment()
                    }
                    .addOnFailureListener { e ->
                        // Handle the failure to retrieve appointments
                        showToast("Failed to retrieve appointments: ${e.message}")
                    }
            }
            .addOnFailureListener { e ->
                // Handle the failure to retrieve doctors
                showToast("Failed to retrieve doctors: ${e.message}")
            }
    }


    private fun getCategoryID(categoryName: String): Int {
        val categoryMapping = mapOf(
            "Cardiology" to 1,
            "Neurology" to 2,
            "Dermatologists" to 3,
            "Otology" to 4,
            "Gastroenterologists" to 5
            // Add more mappings as needed
        )
        return categoryMapping[categoryName] ?: 0
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun displayRecyclerViewAppointment(){
        recyclerViewAppointment = findViewById(R.id.rv_appointments)
        appointmentAdapter = RecyclerViewAdaptadorAppointment(this, appointmentList, categoryName,daySelected, this)
        val layoutManagerAppointment = LinearLayoutManager(this)
        recyclerViewAppointment.layoutManager = layoutManagerAppointment
        recyclerViewAppointment.adapter = appointmentAdapter
    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}
