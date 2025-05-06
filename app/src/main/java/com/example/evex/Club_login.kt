package com.example.evex

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.evex.databinding.ActivityClubLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Club_login : AppCompatActivity() {

    private lateinit var binding: ActivityClubLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityClubLoginBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        //checkUser()

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

//    fun checkUser() {
//        Log.d(TAG, "Check User called")
//        binding.buttonSubmit.setOnClickListener {
//
//            val email = binding.EmailID.text.toString().trim()
//            val password = binding.Password.text.toString().trim()
//            val number = binding.PhoneNumber.toString().trim()
//
//            if (email.isEmpty() || password.isEmpty() || number.isEmpty()){
//                Toast.makeText(baseContext, "please fill all the fields", Toast.LENGTH_SHORT).show()
//            }
//
//            else if (number.length<10){
//                binding.PhoneNumber.error = "Enter valid number"
//                Toast.makeText(baseContext, "Enter valid number", Toast.LENGTH_SHORT).show()
//            }
//            else if (password.length <6){
//                binding.Password.error = "passsword should atleast have 6 char"
//                Toast.makeText(baseContext, "passsword should atleast have 6 char", Toast.LENGTH_SHORT).show()
//            }
//
//           else{
//                auth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success")
//                            val user = auth.currentUser
//                            if (user != null && user.isEmailVerified){
//                                startActivity(Intent(this, MainActivity::class.java))
//                            }
//                            else{
//                                Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT).show()
//                                auth.signOut() // Important! Otherwise user will stay half-logged-in
//                                startActivity(Intent(this, ClubSignup::class.java)) // Maybe you are calling this
//                            }
//
//
//                            //updateUI(user)
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.exception)
//                            Toast.makeText(
//                                baseContext,
//                                "Authentication failed.",
//                                Toast.LENGTH_SHORT,
//                            ).show()
//                            // updateUI(null)
//                        }
//                    }
//            }
//
//
//
//
//        }
//    }




















//    fun IsUserVerified(user: FirebaseUser?){
//        if (user != null && user.isEmailVerified){
//            startActivity(Intent(this, MainActivity::class.java))
//        }
//        else{
//            Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT).show()
//            auth.signOut() // Important! Otherwise user will stay half-logged-in
//            startActivity(Intent(this, ClubSignup::class.java)) // Maybe you are calling this
//        }
//    }
}