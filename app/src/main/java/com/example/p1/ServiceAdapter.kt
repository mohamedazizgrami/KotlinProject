package com.example.p1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ServicesAdapter(private val services: List<Service>) :
    RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder>() {

    class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.serviceName)
        val descriptionTextView: TextView = itemView.findViewById(R.id.serviceDescription)
        val priceTextView: TextView = itemView.findViewById(R.id.servicePrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = services[position]
        holder.nameTextView.text = service.serviceName
        holder.descriptionTextView.text = service.description
        holder.priceTextView.text = "Prix : ${service.price} TND"
    }

    override fun getItemCount(): Int = services.size
}
