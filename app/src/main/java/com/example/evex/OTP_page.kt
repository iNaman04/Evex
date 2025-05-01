package com.example.evex

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.evex.AUTH.AuthViewmodel
import com.example.evex.databinding.ActivityOtpPageBinding
import com.example.evex.utils.Utils
import kotlinx.coroutines.launch

class OTP_page : AppCompatActivity() {

    private lateinit var binding: ActivityOtpPageBinding
    private lateinit var viewmodel: AuthViewmodel

    private lateinit var number : String
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityOtpPageBinding.inflate(layoutInflater)
        viewmodel = ViewModelProvider(this).get(AuthViewmodel::class.java)
        number = intent.getStringExtra("number") ?: ""

        Log.d("OTP", "Received number: $number")

        customizeotp()
        sendOTP()
        login()



        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun verifyotp(otp: String) {
        Log.d("TAG" , "verifyotp")

        val verificationId = viewmodel.verificationId.value
        if (verificationId.isNullOrEmpty()) {
            Toast.makeText(baseContext, "Wait for OTP to be sent...", Toast.LENGTH_SHORT).show()
            Log.e("TAG", "Verification ID not ready")
            return
        }


        viewmodel.signInWithPhoneAuthCredential(otp)
        Log.d("TAG" , "signincredentialcalled")
        lifecycleScope.launch {
            viewmodel.isSignedin.collect{
                if (it){
                    Toast.makeText(baseContext, "Logged in", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun login() {
        Log.d("OTPFragment", "login() function called")
        binding.btnVerifyOtp.setOnClickListener{
            val editText = arrayOf(binding.otp1,binding.otp2,binding.otp3,binding.otp4,binding.otp5,binding.otp6)
            val otp = editText.joinToString { it.text.toString() }
            if (otp.length < editText.size){
                Toast.makeText(baseContext, "Enter a Valid otp", Toast.LENGTH_SHORT).show()
            }
            else{
                verifyotp(otp)
            }

        }
    }





    private fun sendOTP() {

        Log.d("TAG", "send OTP called")

        viewmodel.apply {
            Log.d("TAG", "send OTP called")
            sendOTP(number, this@OTP_page)
            Log.d("TAG", "send OTP called")
            lifecycleScope.launch {
                Log.d("TAG", "send OTP called")
                otpsent.collect{
                    Log.d("TAG", "send OTP called")
                    if (it){
                        Toast.makeText(baseContext, "OTP Sent", Toast.LENGTH_SHORT).show()


                    }
                }

            }
        }
    }

    private fun customizeotp() {
        Log.d("tag","function called1")
        val editText = arrayOf(binding.otp1,binding.otp2,binding.otp3,binding.otp4,binding.otp5,binding.otp6)
        for (i in editText.indices){
            editText[i].addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1){
                        if (i<editText.size - 1){
                            editText[i+1].requestFocus()
                        }
                    }
                    else if(s?.length==0){
                        if (i>0){
                            editText[i-1].requestFocus()
                        }
                    }

                }
            })

        }
    }
}