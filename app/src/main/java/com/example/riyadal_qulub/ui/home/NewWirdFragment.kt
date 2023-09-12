package com.example.riyadal_qulub.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.riyadal_qulub.R

import com.example.riyadal_qulub.databinding.FragmentNewWirdBinding
import com.example.riyadal_qulub.db.WirdDatabase
import com.example.riyadal_qulub.entity.Wird
import com.example.riyadal_qulub.ui.adapter.DaysAdapter
import com.example.riyadal_qulub.utils.getNextSevenDays
import com.example.riyadal_qulub.viewmodel.AddWirdViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

private const val TAG = "NewWirdFragment"

class NewWirdFragment : Fragment() {

    private lateinit var binding: FragmentNewWirdBinding
    private val viewModel: AddWirdViewModel by viewModels()
    private val daysAdapter by lazy { DaysAdapter() }

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
        daysAdapter.onClick = {

            Log.i(TAG, daysAdapter.daysDiffer.currentList.filter { it.isDone }.size.toString())
        }

        setUpdaysRv()
        wirdrepeat()
        getStartingDate()
        getStartingTime()
        wirdAlarm()
        wirdUnit()

        binding.btnAddNewWird.setOnClickListener {
            val wird = Wird(
                id = 0,
                name = binding.etWirdName.editText?.text.toString(),
                startDate = binding.etStartDate.editText?.text.toString(),
                unit = binding.etWirdUnit.editText?.text.toString(),
                quantity = binding.etWirdQuantity.editText?.text.toString().toInt(),
                isAlarm = binding.switchAlarm.isChecked,
                alarmTime = binding.etWirdTime.editText?.text.toString(),
                wirdDays = getWeeksDays(),
            )
            viewModel.addNewWird(database, wird)
            navigateToHome()
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_newWirdFragment_to_homeFragment)
    }

    private fun getWeeksDays(): List<Int> {
        val days = mutableListOf<Int>()
        this.daysAdapter.daysDiffer.currentList.forEach {
            if (it.isDone) {
                //convert month day to day of week number
                val cal = Calendar.getInstance()
                cal.set(Calendar.DAY_OF_MONTH, it.dayMonth)
                days.add(cal.get(Calendar.DAY_OF_WEEK))
            }
        }
        return days
    }


    private fun setUpdaysRv() {


        daysAdapter.daysDiffer.submitList(
            getNextSevenDays()
        )
        binding.rvDays.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, true).apply {

                    isSmoothScrollbarEnabled = false

                }
            adapter = daysAdapter
            setHasFixedSize(true)
        }
    }


    private fun getStartingTime() {
        binding.etWirdTime.editText?.showSoftInputOnFocus = false;
        binding.etWirdTime.editText?.setOnClickListener {
            val timePicker =
                MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("اختر وقت الورد")
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .build()
            timePicker.show(requireFragmentManager(), "tag");
            timePicker.addOnPositiveButtonClickListener {
                lifecycleScope.launch(Dispatchers.Main) {
                    if (timePicker.hour > 12) {
                        timePicker.hour = timePicker.hour - 12
                        val time: String =
                            timePicker.hour.toString() + ":" + timePicker.minute + " مساءً"
                        binding.etWirdTime.editText?.setText(time)

                    } else {
                        val time: String =
                            timePicker.hour.toString() + ":" + timePicker.minute + " صباحاً"
                        binding.etWirdTime.editText?.setText(time)
                    }

                }
            }

        }
    }

    private fun wirdUnit() {
        binding.switchWirdUnit.setOnCheckedChangeListener { buttonView, isChecked ->
            when (isChecked) {
                true -> {
                    binding.etWirdUnit.visibility = View.VISIBLE
                    binding.etWirdQuantity.visibility = View.VISIBLE
                }

                false -> {
                    binding.etWirdUnit.visibility = View.GONE
                    binding.etWirdQuantity.visibility = View.GONE
                }
            }
        }
    }

    private fun wirdAlarm() {
        binding.switchAlarm.setOnCheckedChangeListener { buttonView, isChecked ->
            when (isChecked) {
                true -> binding.etWirdTime.visibility = View.VISIBLE
                false -> binding.etWirdTime.visibility = View.GONE
            }
        }
    }

    private fun getStartingDate() {
        binding.etStartDate.editText?.showSoftInputOnFocus = false;
        binding.etStartDate.editText?.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
            datePicker.show(requireFragmentManager(), "tag")
            datePicker.addOnPositiveButtonClickListener {
                lifecycleScope.launch(Dispatchers.Main) {
                    binding.etStartDate.editText?.setText(datePicker.headerText)
                }
            }
        }
    }

    private fun wirdrepeat() {
        binding.switchRepeatDaily.setOnCheckedChangeListener { buttonView, isChecked ->
            when (isChecked) {
                true -> binding.rvDays.visibility = View.GONE
                false -> binding.rvDays.visibility = View.VISIBLE
            }
        }
    }


}