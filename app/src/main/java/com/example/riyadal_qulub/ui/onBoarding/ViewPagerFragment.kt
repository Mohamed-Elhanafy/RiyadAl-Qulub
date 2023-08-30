package com.example.riyadal_qulub.ui.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.riyadal_qulub.R
import com.example.riyadal_qulub.databinding.FragmentViewPagerBinding
import com.example.riyadal_qulub.ui.onBoarding.screens.FirstScreen
import com.example.riyadal_qulub.ui.onBoarding.screens.SecondScreen
import com.example.riyadal_qulub.ui.onBoarding.screens.ThirdScreen


class ViewPagerFragment : Fragment() {
    lateinit var binding: FragmentViewPagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = FragmentViewPagerBinding.inflate(inflater, container, false)


        val fragmentList =  arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter = ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        binding.boardingViewPager.adapter = adapter

        return binding.root
    }

}