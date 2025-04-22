package com.example.evex

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.evex.databinding.ActivityVerificationPageBinding
import com.google.firebase.auth.FirebaseAuth

class verification_page : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVerificationPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    override fun onResume() {
        super.onResume()
        Log.d("TAG","onResume started")
        binding.btnVerified.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            user?.reload()?.addOnCompleteListener {
                if (user.isEmailVerified){
                    startActivity(Intent(this,Club_description::class.java))
                }
                else{
                    Toast.makeText(this, "Email not verified yet", Toast.LENGTH_SHORT).show()
                }
            }
        }




    }
}