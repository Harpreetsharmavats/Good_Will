package com.example.goodwill.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodwill.Adapter.EventsAdapters
import com.example.goodwill.AddEventActivity
import com.example.goodwill.Models.AddEventDetails
import com.example.goodwill.databinding.FragmentEventBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.collections.ArrayList


class EventFragment : Fragment(),EventsAdapters.OnItemClick {
    private lateinit var binding: FragmentEventBinding
private lateinit var eventList : ArrayList<AddEventDetails>
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        binding.addEventBtn.setOnClickListener {
            startActivity(Intent(requireContext(), AddEventActivity::class.java))
        }
        //To Show and Delete the Event
        showEvents()
        return binding.root


    }

    private fun showEvents() {
        binding.progressBar.visibility = View.VISIBLE
        database = FirebaseDatabase.getInstance()
        eventList = ArrayList()
        val eventRef: DatabaseReference = database.reference.child("Events")
        eventRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (eventSnapshot in snapshot.children){
                    val events = eventSnapshot.getValue(AddEventDetails::class.java)
                    events?.let { eventList.add(it) }
                    setAdapter()
                    binding.progressBar.visibility = View.GONE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Loading Error", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setAdapter() {
        val adapter = EventsAdapters(requireContext(),eventList,this)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }
    override fun onClickDelete(position: Int){

        val key = eventList[position].key
val eventRef = database.getReference("Events").child(key!!)
        eventRef.removeValue().addOnCompleteListener {
            eventList.removeAt(position)
            binding.recyclerView.adapter?.notifyItemRemoved(position)
            Toast.makeText(requireContext(), "Event Deleted Successfully", Toast.LENGTH_SHORT)
                .show()

        }

    }

    companion object {

    }
}