package com.example.evex.AUTH

import android.R
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class AuthViewmodel : ViewModel() {


    private val _selectedCategory = MutableLiveData<String>()
    val selectedCategory: LiveData<String> get() = _selectedCategory

    private var _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> get() = _success

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    fun registerUser(context: Context,email:String,password : String, number : String ,onResult: (Boolean, String) -> Unit){

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                _success.value = true
                Log.d("TAG","user created")
                auth.currentUser?.sendEmailVerification()?.addOnCompleteListener { verify ->
                    if (verify.isSuccessful){

                       // Save_User(uid,email,password,number)
                        Log.d("TAG","link sent to email")
                        onResult(true, task.exception?.localizedMessage ?: "Verification email sent")

                    }
                    else{
                        Log.d("TAG","link sent failed")
                        onResult(false, task.exception?.localizedMessage ?: "user not verified")


                    }
                }
            }
            else{
                onResult(false, task.exception?.localizedMessage ?: "user not created")
                _success.value = false

            }

        }

    }


    fun setSelectedCategory(category: String) {
            _selectedCategory.value = category
    }

//    fun Save_User(uid: String?, email: String, phone: String){
//
//        if (uid == null) return
//
//        val user = User(email, phone)
//
//        FirebaseFirestore.getInstance()
//            .collection("users")
//            .document(uid)
//            .set(user)
//            .addOnSuccessListener {
//                // User saved successfully
//            }
//            .addOnFailureListener {
//                // Handle failure
//            }
//
//    }
//

}