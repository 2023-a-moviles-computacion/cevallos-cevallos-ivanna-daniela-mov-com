package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RecyclerViewAdaptadorUsersAppointments(
    private val contexto : UsersAppointments,
    private val AppointmentList: ArrayList<Appointment>
):RecyclerView.Adapter<RecyclerViewAdaptadorUsersAppointments.MyViewHolder>() {

    inner class MyViewHolder(view:View): RecyclerView.ViewHolder(view){
        val txtDoctorName : TextView
        val txtCategory: TextView
        val txtDate: TextView
        val txtHour : TextView

        init{
            txtDoctorName=view.findViewById(R.id.txt_doctor_myappointment)
            txtCategory = view.findViewById(R.id.txt_category_myapppointment)
            txtDate = view.findViewById(R.id.txt_date_myappointment)
            txtHour = view.findViewById(R.id.txt_hour_myappointment)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recycler_view_users_appointments, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return AppointmentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val usersapppointment = AppointmentList[position]

        holder.txtHour.text = usersapppointment.hour
        holder.txtDate.text = usersapppointment.date
        fetchDoctorInfo(usersapppointment.idMedico!!, object : DoctorInfoCallback {
            override fun onDoctorInfoFetched(doctorName: String, specialityID: Int) {
                // Get the category name using getCategoryID
                val categoryName = getCategoryID(specialityID)

                // Display doctor's name and other appointment details
                holder.txtDoctorName.text = doctorName
                holder.txtHour.text = usersapppointment.hour
                holder.txtDate.text = usersapppointment.date
            }
        })
        fetchSpecialityID(usersapppointment.idMedico!!, object : SpecialityIDCallback {
            override fun onSpecialityIDFetched(specialityID: Int) {
                // Get the category name using getCategoryID
                val categoryName = getCategoryID(specialityID)
                holder.txtCategory.text = categoryName
            }
        })




    }
    interface SpecialityIDCallback {
        fun onSpecialityIDFetched(specialityID: Int)
    }
    private fun fetchSpecialityID(idMedico: String, callback: SpecialityIDCallback) {
        val db = Firebase.firestore
        db.collection("doctors")
            .document(idMedico)
            .get()
            .addOnSuccessListener { doctorDocumentSnapshot ->
                val specialityID = doctorDocumentSnapshot.getLong("specialityID")?.toInt() ?: 0
                callback.onSpecialityIDFetched(specialityID)
            }
            .addOnFailureListener { e ->
            }
    }
    private fun getCategoryID(specialityID: Int): String {
        // Define a mapping of specialityID to category names
        val specialityMapping = mapOf(
            1 to "Cardiology",
            2 to "Neurology",
            3 to "Dermatologists",
            4 to "Otology",
            5 to "Gastroenterologists"
            // Add more mappings as needed
        )

        // Use the specialityID to look up the category name
        return specialityMapping[specialityID] ?: "Unknown" // "Unknown" is the default if no match is found
    }
    private fun fetchDoctorInfo(idMedico: String, callback: DoctorInfoCallback) {
        val db = Firebase.firestore
        db.collection("doctors")
            .document(idMedico)
            .get()
            .addOnSuccessListener { doctorDocumentSnapshot ->
                val doctorName = doctorDocumentSnapshot.getString("name") ?: ""
                val specialityID = doctorDocumentSnapshot.getLong("specialityID")?.toInt() ?: 0
                callback.onDoctorInfoFetched(doctorName, specialityID)
            }
            .addOnFailureListener { e ->
                // Handle the failure here

            }
    }

    fun eliminarRegistro(id:Int){
        var cadena="${id}"
        val db = Firebase.firestore
        val refCocinero= db.collection("appointment")
        refCocinero
            .document(cadena)
            .delete()
            .addOnCompleteListener{  }
            .addOnFailureListener {  }
    }

    interface DoctorInfoCallback {
        fun onDoctorInfoFetched(doctorName: String, specialityID: Int)
    }

}