package com.example.goodwill

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.goodwill.Adapter.DropDownAdapter
import com.example.goodwill.Models.AddEventDetails
import com.example.goodwill.Models.ContributorDetails
import com.example.goodwill.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PaymentResultListener {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var name: String
    private lateinit var phoneNumber: String
    private lateinit var address: String
    private lateinit var email: String
    private lateinit var amount: String

    private lateinit var database: FirebaseDatabase
    val eventName = mutableListOf<String>()
    val organizerName = mutableListOf<String>()
    val eventAddress = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()



        showEventsInAutoComplete()
        binding.backbtn.setOnClickListener {
         finish()
        }

        binding.selectEvent.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.selectEvent.showDropDown()
            }
        }
        binding.makecontribution.setOnClickListener {
            val event = binding.selectEvent.editableText.toString()
            name = binding.name.editText?.text.toString().trim()
            phoneNumber = binding.phonenumber.editText?.text.toString().trim()
            address = binding.address.editText?.text.toString().trim()
            email = binding.email.editText?.text.toString().trim()
            amount = binding.amount.editText?.text.toString().trim()
            if (event.isBlank() ||name.isBlank() || phoneNumber.isBlank() || address.isBlank() || email.isBlank() || amount.isBlank()) {
                Toast.makeText(this, "Please Fill All Details!", Toast.LENGTH_SHORT).show()
            } else {
                startPayment()
            }
        }

    }

    private fun showEventsInAutoComplete() {
        binding.progressBar.visibility = View.VISIBLE

        val eventRef = database.reference.child("Events")
        eventRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists() && snapshot.hasChildren()) {
                for (events in snapshot.children) {
                    val eventLists = events.getValue(AddEventDetails::class.java)
                    eventLists?.eventName?.let { eventName.add(it) }
                    eventLists?.organizerName?.let { organizerName.add(it) }
                    eventLists?.addressOfEvent?.let { eventAddress.add(it) }
                }
                    val adapter =
                        DropDownAdapter(this@MainActivity, eventName, organizerName, eventAddress)
                    binding.selectEvent.setAdapter(adapter)



                }else{
                    Toast.makeText(this@MainActivity, "No events found", Toast.LENGTH_SHORT).show()
                }
                binding.progressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("AutoCompleteTextView", "Error $error")
                Toast.makeText(this@MainActivity, "Failed to load events", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
            }

        })
    }

    private fun startPayment() {
        /*
        *  You need to pass the current activity to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = this
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_EoReUfGodHv2VQ")

        try {
            val options = JSONObject()
            options.put("name", name)
            options.put("description", "Donation")
            options.put("image", "http://example.com/image/rzp.jpg")
            options.put("theme.color", "#3399CC")
            options.put("currency", "INR")
            //options.put("amount", amount)
            options.put("amount", (amount.toFloat() * 100).toInt()) // 500.0 INR => 50000 paise
            options.put("prefill.address", address)
            options.put("prefill.email", email)
            options.put("prefill.contact", phoneNumber)


            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            checkout.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show()
        saveDetails()
    }

    private fun saveDetails() {
        val event = binding.selectEvent.editableText.toString()
        val time = System.currentTimeMillis()
        val key = database.reference.push().key

        val details =
            ContributorDetails(event, name, phoneNumber, address, email, amount, time, key)

        database.reference.child("Contributors").child(key!!).setValue(details).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(this, "Details Sent", Toast.LENGTH_SHORT).show()
                clearDetails()
            }
        }
    }

    private fun clearDetails() {
        binding.selectEvent.editableText?.clear()
        binding.name.editText?.text?.clear()
        binding.phonenumber.editText?.text?.clear()
        binding.address.editText?.text?.clear()
        binding.email.editText?.text?.clear()
        binding.amount.editText?.text?.clear()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Failed: $p1", Toast.LENGTH_SHORT).show()
    }

}