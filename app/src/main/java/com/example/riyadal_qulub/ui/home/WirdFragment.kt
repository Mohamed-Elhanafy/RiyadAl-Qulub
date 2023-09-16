package com.example.riyadal_qulub.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs


import com.example.riyadal_qulub.R
import com.example.riyadal_qulub.databinding.FragmentWirdBinding

private const val TAG = "WirdFragment"


class WirdFragment : Fragment() {
    lateinit var binding: FragmentWirdBinding
    private val args by navArgs<WirdFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWirdBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wird = args.wird
        Log.i(TAG, "wird is : ${wird.name} ")
        binding.apply {
            tvWirdName.text = wird.name
        }
    }

}