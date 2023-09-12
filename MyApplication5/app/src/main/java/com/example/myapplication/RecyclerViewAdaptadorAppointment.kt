package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RecyclerViewAdaptadorAppointment(
    private val contexto: SelectAppointment,
    private val appointmentList: ArrayList<Appointment>,
    private val category : String,
    private val dateAppointment: String,
    private val itemClickListener: RecyclerViewAdaptadorDays.OnItemClickListener
) :RecyclerView.Adapter<RecyclerViewAdaptadorAppointment.MyViewHolder>(){
    private var nameDoctor=""
    private var hourAppointment=""

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val txtDoctor : TextView
        val txtcategory : TextView
        val txtHour : TextView
        val txtMin : TextView

        init {
            txtDoctor = view.findViewById(R.id.txt_name_doctor)
            txtcategory = view.findViewById(R.id.txt_category_doctor)
            txtHour = view.findViewById(R.id.txt_hour_appointment)
            txtMin = view.findViewById(R.id.txt_minute_appointment)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
            .inflate(R.layout.recyler_view_appointments, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val appointment = appointmentList[position]
        val idDoctor = appointment.idMedico

        val db = Firebase.firestore
        db.collection("doctors")
            .document(idDoctor!!)
            .get()
            .addOnSuccessListener { doctorDocumentSnapshot ->
                if (doctorDocumentSnapshot.exists()) {
                    val doctorData = doctorDocumentSnapshot.data
                    val doctorName = doctorData?.get("name") as String
                    val doctorCategory = category
                    // Set the doctor's name to the TextView
                    holder.txtDoctor.text = doctorName
                    holder.txtcategory.text =category
                    val hourMinuteParts = appointment.hour?.split(":")
                    val hour = hourMinuteParts?.get(0)
                    val minute = hourMinuteParts?.get(1)
                    holder.txtHour.text = hour
                    holder.txtMin.text = minute
                    holder.itemView.setOnClickListener {
                        updateAppointmentIdPaciente(appointment.id)
                        // Display a toast with appointment data
                        nameDoctor=doctorName
                        hourAppointment=appointment.hour!!
                        val toastMessage = " Appointment: ${appointment.id} Doctor: $doctorName\nCategory: $doctorCategory\nTime: ${appointment.hour}"
                        Toast.makeText(contexto, toastMessage, Toast.LENGTH_SHORT).show()
                        updateData(appointmentList)
                        irActividad(Confirmation::class.java)
                    }
                } else {
                    // Handle the case where the doctor document doesn't exist
                    holder.txtDoctor.text = "Doctor not found "
                }
            }
            .addOnFailureListener { e ->
                // Handle the failure to retrieve the doctor's data
                holder.txtDoctor.text = "Error: ${e.message}"
            }


    }

    private fun irActividad(java: Class<Confirmation>) {
        val intent = Intent(contexto, java) // Use 'contexto' as the first argument
        intent.putExtra("nameDoctor", nameDoctor)
        intent.putExtra("date", dateAppointment)
        intent.putExtra("hour",hourAppointment )
        contexto.startActivity(intent)
    }

    private fun updateAppointmentIdPaciente(appointmentId: String?) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val idPaciente = user.uid
            if (!appointmentId.isNullOrEmpty()) {
                val db = Firebase.firestore
                val appointmentRef = db.collection("appointments").document(appointmentId)
                appointmentRef
                    .update("idPaciente", idPaciente)
                    .addOnSuccessListener {
                    }
                    .addOnFailureListener { e ->
                    }
            } else {
            }
        } else {
        }
    }

    override fun getItemCount(): Int {
        return appointmentList.size
    }
    fun updateData(newData: ArrayList<Appointment>) {
        appointmentList.clear()
        appointmentList.addAll(newData)
        notifyDataSetChanged()
    }

}
