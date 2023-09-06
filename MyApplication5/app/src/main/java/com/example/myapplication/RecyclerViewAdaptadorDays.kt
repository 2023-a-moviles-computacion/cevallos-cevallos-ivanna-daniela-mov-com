package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RecyclerViewAdaptadorDays (
    private val contexto: SelectAppointment,
    private val dateList: List<String>,
    private val itemClickListener: RecyclerViewAdaptadorDays.OnItemClickListener
): RecyclerView.Adapter<RecyclerViewAdaptadorDays.MyViewHolder>() {
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val DayButton: Button
        init {
            DayButton = view.findViewById(R.id.btn_day_appointment)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recycler_view_days, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    interface OnItemClickListener {
        fun onItemClick(day:String)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val date = dateList[position]
        holder.DayButton.text = date

        holder.DayButton.setOnClickListener {
            itemClickListener.onItemClick(date)
        }
    }
}