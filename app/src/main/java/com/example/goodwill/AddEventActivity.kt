package com.example.goodwill

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.goodwill.Models.AddEventDetails
import com.example.goodwill.databinding.ActivityAddEventBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddEventActivity : AppCompatActivity() {
    private val binding : ActivityAddEventBinding by lazy {
        ActivityAddEventBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var eventName : String
    private lateinit var organizerName : String
    private lateinit var phoneNumber : String
    private lateinit var addressOfEvent : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        setContentView(binding.root)
binding.backbtn.setOnClickListener {
    finish()
}
        binding.addEventBtn.setOnClickListener {
            eventName = binding.eventName.editText?.text.toString().trim()
            organizerName = binding.organizerName.editText?.text.toString().trim()
            phoneNumber = binding.phoneNumber.editText?.text.toString().trim()
            addressOfEvent = binding.addressOfEvent.editText?.text.toString().trim()
            if (eventName.isBlank() || organizerName.isBlank() || phoneNumber.isBlank() || addressOfEvent.isBlank()){
                Toast.makeText(this, "Please Fill All The Details", Toast.LENGTH_SHORT).show()
            }else{
                addEvent()
            }

        }

    }

    private fun addEvent() {
        binding.progressBar.visibility = View.VISIBLE
        val eventRef = database.getReference("Events")
        val key = eventRef.push().key
        val eventDetails = AddEventDetails(eventName, organizerName, phoneNumber, addressOfEvent, key)

        if (key != null) {
            eventRef.child(key).setValue(eventDetails).addOnCompleteListener {
                binding.progressBar.visibility = View.GONE
                if (it.isSuccessful) {
                    Toast.makeText(this, "Data Uploaded Successfully", Toast.LENGTH_SHORT).show()
                    finish()  // Now it's safe to finish the activity
                } else {
                    Toast.makeText(this, "Upload Failed. Please try again.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.progressBar.visibility = View.GONE
            Toast.makeText(this, "Failed to generate event key.", Toast.LENGTH_SHORT).show()
        }
    }

}