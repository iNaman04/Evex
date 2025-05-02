package com.example.evex

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.evex.AUTH.AuthViewmodel
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
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var storedVerificationId: String? = null
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken


    private lateinit var number : String
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityOtpPageBinding.inflate(layoutInflater)
        viewmodel = ViewModelProvider(this).get(AuthViewmodel::class.java)
        number = intent.getStringExtra("number") ?: ""

        Log.d("TAG", "Received number: $number")

//        customizeotp()
//        sendOTP()
//        login()

          sendOTP(number)


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }





    fun sendOTP(number : String){

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d("TAG", "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("TAG", "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                    // reCAPTCHA verification attempted with null Activity
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("TAG", "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token
                OTPcollect()
            }
        }
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91${number}") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)


    }

    private fun OTPcollect() {
        Log.d("TAG", "OTPcollect function called")
        Log.d("TAG", "${number}")
        binding.btnVerifyOtp.setOnClickListener{
            val editText = arrayOf(binding.otp1,binding.otp2,binding.otp3,binding.otp4,binding.otp5,binding.otp6)
            val otp = editText.joinToString(separator = "") { it.text.toString() }
            Log.d("TAG", "${otp}")
            if (otp.length < editText.size){
                Toast.makeText(baseContext, "Enter a Valid otp", Toast.LENGTH_SHORT).show()
            }
            else{
               // verifyotp(otp)
                credential(otp)
            }

        }
    }


    fun credential(code : String){
        if (storedVerificationId != null) {
            Log.d("TAG", "Verifying with ID: $storedVerificationId and code: $code")
            val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, code)
            signInWithPhoneAuthCredential(credential)
        } else {
            Toast.makeText(this, "Wait for OTP to be sent first", Toast.LENGTH_SHORT).show()
        }
    }




    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")

                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }










































//    private fun verifyotp(otp: String) {
//        Log.d("TAG" , "verifyotp")
//
//        val verificationId = viewmodel.verificationId.value
//        if (verificationId.isNullOrEmpty()) {
//            Toast.makeText(baseContext, "Wait for OTP to be sent...", Toast.LENGTH_SHORT).show()
//            Log.e("TAG", "Verification ID not ready")
//            return
//        }
//
//
//        viewmodel.signInWithPhoneAuthCredential(otp)
//        Log.d("TAG" , "signincredentialcalled")
//        lifecycleScope.launch {
//            viewmodel.isSignedin.collect{
//                if (it){
//                    Toast.makeText(baseContext, "Logged in", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//
//    private fun login() {
//        Log.d("OTPFragment", "login() function called")
//        binding.btnVerifyOtp.setOnClickListener{
//            val editText = arrayOf(binding.otp1,binding.otp2,binding.otp3,binding.otp4,binding.otp5,binding.otp6)
//            val otp = editText.joinToString { it.text.toString() }
//            if (otp.length < editText.size){
//                Toast.makeText(baseContext, "Enter a Valid otp", Toast.LENGTH_SHORT).show()
//            }
//            else{
//                verifyotp(otp)
//            }
//
//        }
//    }
//
//
//
//
//
//    private fun sendOTP() {
//
//        Log.d("TAG", "send OTP called")
//
//        viewmodel.apply {
//            Log.d("TAG", "send OTP called")
//            sendOTP(number, this@OTP_page)
//            Log.d("TAG", "send OTP called")
//            lifecycleScope.launch {
//                Log.d("TAG", "send OTP called")
//                otpsent.collect{
//                    Log.d("TAG", "send OTP called")
//                    if (it){
//                        Toast.makeText(baseContext, "OTP Sent", Toast.LENGTH_SHORT).show()
//
//
//                    }
//                }
//
//            }
//        }
//    }
//
//    private fun customizeotp() {
//        Log.d("tag","function called1")
//        val editText = arrayOf(binding.otp1,binding.otp2,binding.otp3,binding.otp4,binding.otp5,binding.otp6)
//        for (i in editText.indices){
//            editText[i].addTextChangedListener(object : TextWatcher {
//                override fun afterTextChanged(s: Editable?) {
//                }
//
//                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    if (s?.length == 1){
//                        if (i<editText.size - 1){
//                            editText[i+1].requestFocus()
//                        }
//                    }
//                    else if(s?.length==0){
//                        if (i>0){
//                            editText[i-1].requestFocus()
//                        }
//                    }
//
//                }
//            })
//
//        }
//    }
}