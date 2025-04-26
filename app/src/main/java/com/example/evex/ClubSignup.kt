package com.example.evex

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.evex.AUTH.AuthViewmodel
import com.example.evex.AUTH.Data_Viewmodel
import com.example.evex.databinding.ActivityClubSignupBinding
import com.example.evex.utils.Utils
import com.google.firebase.auth.FirebaseAuth

class ClubSignup : AppCompatActivity() {

    private lateinit var binding: ActivityClubSignupBinding
    private lateinit var viewmodel: AuthViewmodel
    private lateinit var dataViewmodel: Data_Viewmodel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityClubSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodel = ViewModelProvider(this)[AuthViewmodel::class.java]
        dataViewmodel = ViewModelProvider(this)[Data_Viewmodel::class.java]
        checkCredentials()

        loginPage()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun loginPage() {
        binding.loginTextView.setOnClickListener{
            val intent = Intent(this,Club_login::class.java)
            startActivity(intent)
        }
    }


    fun checkCredentials(){

       binding.sendOtpButton.setOnClickListener {
           val email = binding.emailEditText.text.toString()
           val password = binding.passwordEditText.text.toString()
           val Cpassword = binding.confirmPasswordEditText.text.toString()
           val number = binding.phoneEditText.text.toString()

           if (email.isEmpty() || password.isEmpty() || Cpassword.isEmpty() || number.isEmpty() ){
               Toast.makeText(baseContext, "Empty Field", Toast.LENGTH_SHORT).show()
           }

           else if (number.length<10){
               binding.phoneEditText.error = "Enter valid number"
               Toast.makeText(baseContext, "Enter valid number", Toast.LENGTH_SHORT).show()
           }
           else if (password!=Cpassword){
               binding.passwordEditText.error = "password dont match"
               binding.confirmPasswordEditText.error = "password dont match"
           }

           else{
               Utils.showVerificationDialog(this)
               viewmodel.registerUser(this,email,password,number) {success, message ->
                   Utils.dismissVerificationDialog()

                   if (success) {
                       dataViewmodel.saveUserData(number)
                       Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
                       startActivity(Intent(this, verification_page::class.java))
                   } else {
                       Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
                   }



               }
           }

       }




    }




}