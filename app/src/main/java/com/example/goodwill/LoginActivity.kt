package com.example.goodwill

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.goodwill.Models.AdminUser
import com.example.goodwill.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var email: String
    private lateinit var password: String
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        binding.continueAsContributor.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.loginbtn.setOnClickListener {
            email = binding.Email.editText?.text.toString().trim()
            password = binding.pass.editText?.text.toString().trim()
            signIn(email, password)
        }

    }

    private fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AdminMainActivity::class.java))
                //savedetails()
            } else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()

            }
        }
    }

    /*private fun savedetails() {
        email = binding.Email.editText?.text.toString().trim()
        password = binding.pass.editText?.text.toString().trim()
val user =AdminUser(email,password)
val userId : String = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("Admins").child(userId).setValue(user)

    }*/
}