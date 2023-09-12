package com.example.riyadal_qulub.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.riyadal_qulub.R

import com.example.riyadal_qulub.databinding.FragmentNewWirdBinding
import com.example.riyadal_qulub.db.WirdDatabase
import com.example.riyadal_qulub.entity.Wird
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewWirdFragment : Fragment() {
    private lateinit var binding: FragmentNewWirdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewWirdBinding.inflate(LayoutInflater.from(context), container, false)
        //database = WirdDatabase.getDatabase(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = WirdDatabase.getDatabase(requireContext())


        binding.btnAddNewWird.setOnClickListener {

            lifecycleScope.launch(Dispatchers.IO) {
                database.wirdDao().insertWird(
                    Wird()
                )
            }
        }
    }


}