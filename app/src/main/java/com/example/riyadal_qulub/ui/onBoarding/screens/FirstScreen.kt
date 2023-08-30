package com.example.riyadal_qulub.ui.onBoarding.screens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.riyadal_qulub.databinding.FragmentBoardingScreen1Binding
import com.example.riyadal_qulub.ui.home.HomeActivity
import com.example.riyadal_qulub.utils.onBoardingFinished

class FirstScreen : Fragment() {

    private lateinit var binding: FragmentBoardingScreen1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardingScreen1Binding.inflate(inflater, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(com.example.riyadal_qulub.R.id.boarding_view_pager)

        binding.btnNext.setOnClickListener {
            viewPager?.currentItem = 1
        }

        binding.tvSkip.setOnClickListener {
            onBoardingFinished(requireActivity())
            Intent (activity, HomeActivity::class.java).also {
                startActivity(it)
                activity?.finish()
            }
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}