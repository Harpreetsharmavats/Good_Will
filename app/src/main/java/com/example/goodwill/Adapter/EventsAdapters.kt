package com.example.goodwill.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.goodwill.Models.AddEventDetails
import com.example.goodwill.databinding.EventdetailsBinding

class EventsAdapters(private val context: Context, private val events: MutableList<AddEventDetails>,val itemClicked : OnItemClick) :
    RecyclerView.Adapter<EventsAdapters.EventsViewHolder>() {
interface OnItemClick{
    fun onClickDelete(position: Int)
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(
            EventdetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class EventsViewHolder(private val binding: EventdetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val event = events[position]
            binding.eventName.text = event.eventName
            binding.organizerName.text = event.organizerName
            binding.address.text = event.addressOfEvent
            binding.deleteBtn.setOnClickListener {
                val adapterPosition = adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {

                    itemClicked.onClickDelete(position)
                }


            }
        }

    }


}