package com.example.riyadal_qulub.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.riyadal_qulub.R
import com.example.riyadal_qulub.databinding.FragmentHomeBinding
import com.example.riyadal_qulub.db.WirdDatabase
import com.example.riyadal_qulub.entity.Wird
import com.example.riyadal_qulub.ui.adapter.WirdAdapter
import com.example.riyadal_qulub.utils.getCurrentDate
import com.example.riyadal_qulub.viewmodel.HomeViewModel

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewmodel by viewModels<HomeViewModel>()
    private val wirdAdapter by lazy { WirdAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = WirdDatabase.getDatabase(requireContext())
        viewmodel.getAllWirds(database)
        observeWirds()


        settingUpWirdRv()

        binding.btnAddWird.setOnClickListener {
            findNavController()
                .navigate(R.id.action_homeFragment_to_newWirdFragment)
        }

        wirdAdapter.setOnButtonClickListener {
            setUpDoneBtn(it, database)
        }
        wirdAdapter.onClick = {
            Log.i(TAG, "wird: $it")
            findNavController().navigate(R.id.action_homeFragment_to_wirdFragment)
        }

        wirdAdapter.setOnDayClickListener {
            Log.i(TAG, "Day clicked: ${it.day} ")
        }


    }

    private fun setUpDoneBtn(
        it: Wird,
        database: WirdDatabase
    ) {
        val newDates = mutableListOf<String>()
        val doneDays = it.doneDays
        checkIfCurrentDateIsInList(doneDays, newDates)
        viewmodel.updateDoneDays(database, it.id, newDates)
        viewmodel.updateIsDone(database, it.id, it.isDone)
        Log.i(TAG, "newDates: $newDates")
        Log.i(TAG, "wirds: ${viewmodel.wirds.value!!}")
    }

    private fun checkIfCurrentDateIsInList(
        currentDates: List<String>,
        newDates: MutableList<String>
    ) {
        currentDates.forEach {
            if (it == getCurrentDate()) {
                newDates.add(it)
            }
        }
        if (newDates.isEmpty()) {
            newDates.add(getCurrentDate())
        }

    }


    private fun settingUpWirdRv() {
        binding.wirdRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = wirdAdapter

        }


    }


    private fun observeWirds() {
        viewmodel.wirds.observe(viewLifecycleOwner) {
            Log.i(TAG, "observeWirds:item count ${it.size}")
            wirdAdapter.differ.submitList(it)
            if (viewmodel.wirds.value != null) {
                Log.i(TAG, "onViewCreated: ${viewmodel.wirds.value!!.size}")
                binding.emptyAnimation.visibility = View.GONE
            } else {
                Log.i(TAG, "onViewCreated: null")
            }
        }
    }


}