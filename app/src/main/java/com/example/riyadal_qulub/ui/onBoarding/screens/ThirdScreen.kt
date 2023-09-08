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
import com.example.riyadal_qulub.ui.authentication.AuthenticationActivity
import com.example.riyadal_qulub.utils.Constant.boardingFinished
import com.example.riyadal_qulub.utils.Constant.boardingSharedPref
import com.example.riyadal_qulub.utils.onBoardingFinished


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
            onBoardingFinished(requireActivity())
        }
    }

    private fun navigateToHome() {
       Intent (activity, AuthenticationActivity::class.java).also {
           startActivity(it)
           activity?.finish()
       }
    }


}