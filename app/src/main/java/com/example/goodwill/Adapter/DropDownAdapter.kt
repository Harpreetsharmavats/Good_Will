package com.example.goodwill.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.goodwill.databinding.DropdownlayoutBinding

class DropDownAdapter(context: Context, private val eventName: List<String>,private val organizerName: List<String>,private val eventAddres : List<String>) :
    ArrayAdapter<String>(context, 0, eventName) {

    override fun getView(position: Int, convertView: android.view.View?, parent: ViewGroup): android.view.View {
        val binding = DropdownlayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        val event = eventName[position]
        val organizerName = organizerName[position]
        val address = eventAddres[position]
        binding.eventName.text = event
binding.organizerName.text= organizerName
        binding.address.text = address

        return binding.root
    }
}