package com.example.goodwill.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.goodwill.Models.ContributorDetails
import com.example.goodwill.databinding.ContributorslayoutBinding

class ContributorsAdapter (context: Context,private val details :List<ContributorDetails>) : RecyclerView.Adapter<ContributorsAdapter.ContributorViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorViewHolder {
        return ContributorViewHolder(ContributorslayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = details.size

    override fun onBindViewHolder(holder: ContributorViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ContributorViewHolder(private val binding: ContributorslayoutBinding) : ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.name.text = details[position].name
            binding.phoneNumber.text = details[position].phoneNumber
            binding.address.text = details[position].address
            binding.amount.text = details[position].amount
            binding.eventName.text = details[position].event
        }

    }
}