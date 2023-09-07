package com.example.riyadal_qulub

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.riyadal_qulub.ui.authentication.AuthenticationActivity
import com.example.riyadal_qulub.ui.home.HomeActivity
import com.example.riyadal_qulub.ui.onBoarding.OnBoardingActivity
import com.example.riyadal_qulub.utils.Constant
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (onBoardingFinished()) {
            if (FirebaseAuth.getInstance().currentUser != null) {
                navigateToHome()
            } else {
                navigateToAuth()
            }
        } else {
            navigateToOnBoarding()
        }
    }

    private fun navigateToAuth() {
        Intent(this, AuthenticationActivity::class.java).also {
            startActivity(it)
            finish()
        }    }

    private fun navigateToOnBoarding() {
        Intent(this, OnBoardingActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun navigateToHome() {
        Intent(this, HomeActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun onBoardingFinished(): Boolean {

        val sharedPref =
            this?.getSharedPreferences(Constant.boardingSharedPref, Context.MODE_PRIVATE)
        return sharedPref!!.getBoolean(Constant.boardingFinished, false)
    }
}