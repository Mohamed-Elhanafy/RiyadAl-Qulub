package com.example.riyadal_qulub.ui.onBoarding.screens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.riyadal_qulub.R

import com.example.riyadal_qulub.databinding.FragmentBoardingScreen3Binding
import com.example.riyadal_qulub.utils.Constant.boardingFinished
import com.example.riyadal_qulub.utils.Constant.boardingSharedPref


class ThirdScreen : Fragment() {

    private lateinit var binding: FragmentBoardingScreen3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardingScreen3Binding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            navigateToHome()
            onBoardingFinished()
        }
    }

    private fun navigateToHome() {
       Intent (activity, com.example.riyadal_qulub.ui.home.HomeActivity::class.java).also {
           startActivity(it)
           activity?.finish()
       }
    }

    private fun onBoardingFinished() {
        val sharedPref = activity?.getSharedPreferences(boardingSharedPref, 0)
        val editor = sharedPref?.edit()
        editor?.putBoolean(boardingFinished, true)
        editor?.apply()
    }
}