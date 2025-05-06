package com.example.evex.AUTH

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.evex.data_classes.ClubUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Data_Viewmodel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    var _userSaveSuccess = MutableLiveData<Boolean>()
    val userSaveSuccess: LiveData<Boolean> get() = _userSaveSuccess

    var _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> get() = _errorMessage

    fun saveUserData(email: String, phone: String) {

        val user = hashMapOf(
            "email" to email,
            "phone" to phone
        )

        val uid = auth.currentUser?.uid
        if (uid != null) {
            db.collection("users").document(uid)
                .set(user)
                .addOnSuccessListener {
                    Log.d("TAG", "User data stored")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error storing user data", e)
                }
        } else {
            Log.w("TAG", "User UID is null")
        }
    }

    fun saveClubData(clubName: String, collegeName: String, universityName: String, description: String, category: String) {
        val club = hashMapOf(
            "clubName" to clubName,
            "collegeName" to collegeName,
            "universityName" to universityName,
            "description" to description,
            "category" to category
        )

        val uid = auth.currentUser?.uid
        if (uid != null) {
            // Store club data in the 'clubs' sub-collection under the user's document
            db.collection("users")
                .document(uid)
                .collection("clubs")
                .document() // This creates a new document with a unique ID in the 'clubs' collection
                .set(club)
                .addOnSuccessListener {
                    Log.d("TAG", "Club data stored successfully")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error storing club data", e)
                }
        } else {
            Log.w("TAG", "User UID is null")
        }
    }


















    fun createClubuser(email : String, password : String){

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    _userSaveSuccess.value = true
                }
                else{
                    _userSaveSuccess.value = false
                }
            }


    }




}