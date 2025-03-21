package com.example.goodwill

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.goodwill.Adapter.DropDownAdapter
import com.example.goodwill.Models.AddEventDetails
import com.example.goodwill.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    val eventName = mutableListOf<String>()
    val organizerName = mutableListOf<String>()
    val eventAddress = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backbtn.setOnClickListener {
            finish()
        }

        binding.selectEvent.setOnClickListener {
            showEventsInAutoComplete()
            binding.selectEvent.showDropDown()
        }

    }

    private fun showEventsInAutoComplete() {
        binding.progressBar.visibility = View.VISIBLE
        database = FirebaseDatabase.getInstance()
        val eventRef = database.reference.child("Events")
        eventRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (events in snapshot.children) {
                    val eventLists = events.getValue(AddEventDetails::class.java)
                    eventLists?.eventName?.let { eventName.add(it) }
                    eventLists?.organizerName?.let { organizerName.add(it) }
                    eventLists?.addressOfEvent?.let { eventAddress.add(it) }
                    val adapter =
                        DropDownAdapter(this@MainActivity, eventName, organizerName, eventAddress)
                    binding.selectEvent.setAdapter(adapter)
                    binding.progressBar.visibility = View.GONE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("AutoCompleteTextView", "Error $error")
            }

        })
    }
}