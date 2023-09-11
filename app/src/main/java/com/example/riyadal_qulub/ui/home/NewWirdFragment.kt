package com.example.riyadal_qulub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.riyadal_qulub.R

import com.example.riyadal_qulub.databinding.FragmentNewWirdBinding

class NewWirdFragment : Fragment() {
    private lateinit var binding: FragmentNewWirdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewWirdBinding.inflate(LayoutInflater.from(context), container, false)

        return binding.root
    }


}