package com.example.goodwill.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.goodwill.AddEventActivity
import com.example.goodwill.databinding.FragmentAddEventBinding


class AddEventFragment : Fragment() {
    private lateinit var binding: FragmentAddEventBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEventBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        binding.addEventBtn.setOnClickListener {
            startActivity(Intent(requireContext(), AddEventActivity::class.java))
        }
        return binding.root


    }

    companion object {

    }
}