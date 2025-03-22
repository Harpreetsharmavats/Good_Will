package com.example.goodwill

import android.app.Activity
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
    private lateinit var name:String
    private lateinit var phoneNumber:String
    private lateinit var address:String
    private lateinit var email:String
    private lateinit var amount:String

    private lateinit var database: FirebaseDatabase
    val eventName = mutableListOf<String>()
    val organizerName = mutableListOf<String>()
    val eventAddress = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()

        name = binding.name.editText?.text.toString().trim()
        phoneNumber = binding.phonenumber.editText?.text.toString().trim()
        address = binding.address.editText?.text.toString().trim()
        email = binding.email.editText?.text.toString().trim()
        amount = binding.amount.editText?.text.toString().trim()

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
            if (name.isBlank() || phoneNumber.isBlank() || address.isBlank() || email.isBlank() || amount.isBlank()){
                Toast.makeText(this, "Please Fill All Details!", Toast.LENGTH_SHORT).show()
            }else{

            }
        }

    }

    private fun showEventsInAutoComplete() {
        binding.progressBar.visibility = View.VISIBLE

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

    private fun startPayment() {
        /*
        *  You need to pass the current activity to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = this
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_EoReUfGodHv2VQ")

        try {
            val options = JSONObject()
            options.put("name",name)
            options.put("description","Donation")
            options.put("image","http://example.com/image/rzp.jpg")
            options.put("theme.color", "#3399CC")
            options.put("currency","INR")
            options.put("order_id", "order_DBJOWzybf0sJbb")
            options.put("amount",amount)
            options.put("prefill.address",address)
            options.put("prefill.email",email)
            options.put("prefill.contact",phoneNumber)


            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            checkout.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        saveDetails()
    }

    private fun saveDetails() {
        val time = System.currentTimeMillis()
        val key = database.reference.push().key
val details = ContributorDetails(name,phoneNumber, address, email, amount,time,key)

        database.reference.child("Contributors").child(key!!).setValue(details)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Log.d("Payment","Payment Failure $p0 $p1")
    }

}