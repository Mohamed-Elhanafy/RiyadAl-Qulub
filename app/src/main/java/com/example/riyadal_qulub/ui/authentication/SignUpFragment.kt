package com.example.riyadal_qulub.ui.authentication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.riyadal_qulub.R
import com.example.riyadal_qulub.databinding.FragmentViewPagerBinding
import com.example.riyadal_qulub.viewmodel.SignUpViewModel

private const val TAG = "SignUpFragment"
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentViewPagerBinding
    private val viewmodel: SignUpViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
        Log.i(TAG, "onCreateView: ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.signUp("test@gmail.com", "123456")
    }
}