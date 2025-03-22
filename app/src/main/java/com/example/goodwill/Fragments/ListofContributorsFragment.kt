package com.example.goodwill.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodwill.Adapter.ContributorsAdapter
import com.example.goodwill.Models.AddEventDetails
import com.example.goodwill.Models.ContributorDetails
import com.example.goodwill.R
import com.example.goodwill.databinding.FragmentListofContributorsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ListofContributorsFragment : Fragment() {
    private lateinit var binding : FragmentListofContributorsBinding
private lateinit var database : FirebaseDatabase
private lateinit var contributorsList : ArrayList<ContributorDetails>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListofContributorsBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        showContributors()
        return binding.root
    }
    private fun showContributors() {
        binding.progressBar.visibility = View.VISIBLE
        database = FirebaseDatabase.getInstance()
        contributorsList = ArrayList()
        val eventRef: DatabaseReference = database.reference.child("Contributors")
        eventRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (eventSnapshot in snapshot.children){
                    val events = eventSnapshot.getValue(ContributorDetails::class.java)
                    events?.let { contributorsList.add(it) }
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
        val adapter = ContributorsAdapter(requireContext(),contributorsList)
        binding.recyclerView.layoutManager =LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    companion object {

    }
}