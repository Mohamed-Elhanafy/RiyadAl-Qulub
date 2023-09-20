package com.example.riyadal_qulub.ui.statistics

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.riyadal_qulub.databinding.FragmentStatisticsBinding
import com.example.riyadal_qulub.db.WirdDatabase
import com.example.riyadal_qulub.ui.adapter.StatisticsAdapter
import com.example.riyadal_qulub.utils.convertStringToLocalDate
import com.example.riyadal_qulub.viewmodel.HomeViewModel
import java.time.LocalDate

private const val TAG = "StatisticsFragment"

class StatisticsFragment : Fragment() {

    private lateinit var binding: FragmentStatisticsBinding
    private val viewmodel by viewModels<HomeViewModel>()
    private val statisticsAdapter by lazy { StatisticsAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatisticsBinding.inflate(LayoutInflater.from(context), container, false)
        val database = WirdDatabase.getDatabase(requireContext().applicationContext)
        observeWirds(database)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        setupCardView()
    }

    private fun setupCardView() {

    }

    private fun setupRecyclerView() {
        binding.rvStat.apply {
            //linear layout manager with vertical orientation to get full width of the screen
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = statisticsAdapter
            //

        }
    }


    @SuppressLint("NewApi")
    private fun observeWirds(database: WirdDatabase) {
        viewmodel.getAllWirds(database)
        viewmodel.wirds.observe(viewLifecycleOwner) {
            Log.i(TAG, "observeWirds:item count ${it.size}")
            statisticsAdapter.differ.submitList(it)


            //i need to filter wird.doneDays to get only the days of this month
            val currentMonthWirds = mutableListOf<String>()
            val doneWirdsThisMonth = mutableListOf<String>()
            it.forEach { wird ->
                val listOfDoneDays = wird.doneDays
                listOfDoneDays.forEach { doneDate ->
                    val date = convertStringToLocalDate(doneDate)
                    val day = date.dayOfMonth
                    val month = date.monthValue
                    val year = date.year
                    val dateText = "$day/$month/$year"

                    if (date.monthValue == LocalDate.now().monthValue) {
                        Log.i(TAG, "setUpCardView: $dateText")
                        doneWirdsThisMonth.add(dateText)
                    }

                }

                if (doneWirdsThisMonth.size > 0) {
                    currentMonthWirds.add(wird.name)
                }
            }

            binding.apply {
                tvMonthlyCount.text = doneWirdsThisMonth.size.toString()
                tvMonthPrcCount.text =
                    "${
                        ((doneWirdsThisMonth.size.toFloat() / (LocalDate.now()
                            .lengthOfMonth() * it.size.toFloat())) * 100).toInt()
                    } %"
            }
        }


    }
}