package com.example.evex.AUTH

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.evex.data_classes.ClubUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Data_Viewmodel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    val _userSaveSuccess = MutableLiveData<Boolean>()
    val userSaveSuccess: LiveData<Boolean> get() = _userSaveSuccess

    val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> get() = _errorMessage

    fun saveUserData(number : String){

        val uid = auth.currentUser?.uid
        val email = auth.currentUser?.email

        if (uid != null && email!= null){
            val user = ClubUser(email = email , phoneNumber = number)
            db.collection("ClubUsers").document(uid).set(user)
                .addOnSuccessListener {
                    _userSaveSuccess.value = true
                }
                .addOnFailureListener {
                    _errorMessage.value = it.message
                }
        }
        else{
            _errorMessage.value = "User not authenticated"
        }


    }





}