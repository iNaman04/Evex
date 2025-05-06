package com.example.evex

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.evex.AUTH.AuthViewmodel
import com.example.evex.AUTH.Data_Viewmodel
import com.example.evex.databinding.ActivityOtpPageBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import java.util.concurrent.TimeUnit

class OTP_page : AppCompatActivity() {

    private lateinit var binding: ActivityOtpPageBinding
    private lateinit var viewmodel: AuthViewmodel
    private lateinit var Dataviewmodel : Data_Viewmodel
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var storedVerificationId: String? = null
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken


    private lateinit var number : String
    private lateinit var email : String

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityOtpPageBinding.inflate(layoutInflater)
        viewmodel = ViewModelProvider(this).get(AuthViewmodel::class.java)
        Dataviewmodel = ViewModelProvider(this).get(Data_Viewmodel::class.java)

        email = intent.getStringExtra("email") ?: ""
        number = intent.getStringExtra("number") ?: ""

        Log.d("TAG", "Received number: $number")


        customizeotp()
        observers()
        viewmodel.sendOTP(number,this)


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.btnVerifyOtp.setOnClickListener{
            val editText = arrayOf(binding.otp1,binding.otp2,binding.otp3,binding.otp4,binding.otp5,binding.otp6)
            val otp = editText.joinToString(separator = "") { it.text.toString() }
            Log.d("TAG", "${otp}")
            if (otp.length < editText.size){
                Toast.makeText(baseContext, "Enter a Valid otp", Toast.LENGTH_SHORT).show()
            }
            else{
                // verifyotp(otp)
                viewmodel.credential(otp)
            }

        }

    }

    fun observers(){
        viewmodel.otpSent.observe(this) {
            if (it) Log.d("TAG", "OTP sent successfully")
        }

        viewmodel.verificationSuccess.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Verification successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Club_description::class.java))
                Dataviewmodel.saveUserData(email,number)

                // Navigate to next screen
            } else {
                Toast.makeText(this, "Verification failed", Toast.LENGTH_SHORT).show()
            }
        }

        viewmodel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
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
















































