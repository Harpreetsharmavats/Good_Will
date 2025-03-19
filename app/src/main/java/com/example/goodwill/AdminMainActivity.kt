package com.example.goodwill

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.goodwill.databinding.ActivityAdminMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminMainActivity : AppCompatActivity() {
    private val binding: ActivityAdminMainBinding by lazy {
        ActivityAdminMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

val bottomNav : BottomNavigationView = binding.adminBottomNav
        val navController= findNavController(R.id.fragmentContainerView)
        bottomNav.setupWithNavController(navController)

    }
}