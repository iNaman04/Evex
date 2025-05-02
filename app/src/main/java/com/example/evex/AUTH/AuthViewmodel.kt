package com.example.evex.AUTH

import android.R
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.evex.utils.Utils
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit


class AuthViewmodel : ViewModel() {


    private val _selectedCategory = MutableLiveData<String>()
    val selectedCategory: LiveData<String> get() = _selectedCategory

    private var _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> get() = _success

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore


    private val _VerificationId = MutableStateFlow<String?>(null)
    val verificationId = _VerificationId.asStateFlow()
    private val _otpsent = MutableStateFlow(false)
    val otpsent = _otpsent.asStateFlow()
    private val _isSignedin = MutableStateFlow(false)
    val isSignedin = _isSignedin
























//
//    fun registerUser(context: Context,email:String,password : String, number : String ,onResult: (Boolean, String) -> Unit){
//
//        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
//            if(task.isSuccessful){
//                _success.value = true
//                Log.d("TAG","user created")
//                auth.currentUser?.sendEmailVerification()?.addOnCompleteListener { verify ->
//                    if (verify.isSuccessful){
//
//                       // Save_User(uid,email,password,number)
//                        Log.d("TAG","link sent to email")
//                        onResult(true, task.exception?.localizedMessage ?: "Verification email sent")
//
//                    }
//                    else{
//                        Log.d("TAG","link sent failed")
//                        onResult(false, task.exception?.localizedMessage ?: "user not verified")
//
//
//                    }
//                }
//            }
//            else{
//                onResult(false, task.exception?.localizedMessage ?: "user not created")
//                _success.value = false
//
//            }
//
//        }
//
//    }
//
//
    fun setSelectedCategory(category: String) {
            _selectedCategory.value = category
    }
//
//    fun sendOTP(number: String , activity :Activity){
//
//
//        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//
//            }
//
//            override fun onVerificationFailed(e: FirebaseException) {
//
//                Log.w(TAG, "onVerificationFailed", e)
//
//                if (e is FirebaseAuthInvalidCredentialsException) {
//                    // Invalid request
//                } else if (e is FirebaseTooManyRequestsException) {
//                    // The SMS quota for the project has been exceeded
//                } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
//                    // reCAPTCHA verification attempted with null Activity
//                }
//
//                // Show a message and update the UI
//            }
//
//            override fun onCodeSent(
//                verificationId: String,
//                token: PhoneAuthProvider.ForceResendingToken,
//            ) {
//                // The SMS verification code has been sent to the provided phone number, we
//                // now need to ask the user to enter the code and then construct a credential
//                // by combining the code with a verification ID.
//                Log.d(TAG, "onCodeSent:$verificationId")
//
//                // Save verification ID and resending token so we can use them later
//                _VerificationId.value = verificationId
//                _otpsent.value = true
//            }
//        }
//
//        val formattedNumber = if (!number.startsWith("+")) "+91$number" else number
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(formattedNumber) // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(activity) // Activity (for callback binding)
//            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//
//
//    }
//
//
//    fun signInWithPhoneAuthCredential(otp : String) {
//
//        val verificationId = _VerificationId.value
//        if (verificationId == null) {
//            Log.e(TAG, "Verification ID is null - OTP cannot be verified")
//            return
//        }
//
//        val credential = PhoneAuthProvider.getCredential(verificationId, otp)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d(TAG, "Sign-in success")
//                    _isSignedin.value = true
//                } else {
//                    Log.d(TAG, "Sign-in failed", task.exception)
//                    _isSignedin.value = false
//                    // Handle specific error cases
//                    when (task.exception) {
//                        is FirebaseAuthInvalidCredentialsException -> {
//                            Log.e(TAG, "Invalid OTP entered")
//                        }
//                        else -> {
//                            Log.e(TAG, "Verification failed: ${task.exception?.message}")
//                        }
//                    }
//                }
//            }
//    }

}