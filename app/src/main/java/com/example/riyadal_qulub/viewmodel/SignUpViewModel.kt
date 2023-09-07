package com.example.riyadal_qulub.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "SignUpViewModel"

class SignUpViewModel() : ViewModel() {
    val fireBaseAuth = FirebaseAuth.getInstance()

    fun signUp(email: String, password: String) {
        fireBaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.i(TAG, "createUserWithEmail:success")
            } else {
                Log.i(TAG, "createUserWithEmail:failure", it.exception)
            }

        }


    }
}