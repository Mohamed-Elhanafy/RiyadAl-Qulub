package com.example.riyadal_qulub.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.riyadal_qulub.R
import com.example.riyadal_qulub.databinding.FragmentHomeBinding
import com.example.riyadal_qulub.db.WirdDatabase
import com.example.riyadal_qulub.entity.WeekDayItem
import com.example.riyadal_qulub.entity.Wird
import com.example.riyadal_qulub.ui.adapter.WirdAdapter
import com.example.riyadal_qulub.utils.PreferenceKeys
import com.example.riyadal_qulub.utils.getCurrentDate
import com.example.riyadal_qulub.viewmodel.HomeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
        wirdAdapter.notifyDataSetChanged()
        val database = WirdDatabase.getDatabase(requireContext().applicationContext)
        viewmodel.getAllWirds(database)

        observeWirds()


        settingUpWirdRv()
        settingUpHeader()
        binding.btnAddWird.setOnClickListener {
            findNavController()
                .navigate(R.id.action_homeFragment_to_newWirdFragment)
        }

        wirdAdapter.setOnButtonClickListener {
            if (currentDateIsInList(getCurrentDate(), it.doneDays)) {
                viewmodel.removeDayFromDoneDays(database, it.id, getCurrentDate())
                viewmodel.updateIsDone(database, it.id, false)
                wirdAdapter.notifyDataSetChanged()

            } else {
                viewmodel.addDayToDoneDays(database, it.id, getCurrentDate())
                viewmodel.updateIsDone(database, it.id, true)
                wirdAdapter.notifyDataSetChanged()
            }

            //todo set up some kind of progress indicator and way to redo the wird
        }

        wirdAdapter.onClick = { wird ->
            Log.i(TAG, "wird: $wird")
            val b = Bundle().apply {
                putParcelable("wird", wird)
            }
            findNavController().navigate(R.id.action_homeFragment_to_wirdFragment, b)

        }
        settingUpOnLongClick(database)

        wirdAdapter.setOnDayClickListener {
            val calendar = Calendar.getInstance()
            val arabicLocale = Locale("ar", "SA") // Use Arabic locale
            val dateFormat = SimpleDateFormat("EEEE", arabicLocale)
            val dateFormat2 = SimpleDateFormat("dd MMMM yyyy", arabicLocale)

            val dateNumbers = mutableListOf<String>()
            var dayOfWeek: String = ""

            for (i in 0 until 7) {
                val thisDay = dateFormat.format(calendar.time)
                if (thisDay == it.day) {
                    dayOfWeek = dateFormat2.format(calendar.time)
                    dateNumbers.add(dayOfWeek)
                    Log.i(TAG, "dayOfWeek: $dayOfWeek")

                }
                calendar.add(Calendar.DAY_OF_MONTH, -1)
            }
            var wird = wirdAdapter.wird2
            //check if the day is in the list of wird.doneDays
            if (!currentDateIsInList(dayOfWeek, wird!!.doneDays)) {
                viewmodel.addDayToDoneDays(database, wird.id, dayOfWeek)
                wirdAdapter.notifyDataSetChanged()
            } else {
                viewmodel.removeDayFromDoneDays(database, wird.id, dayOfWeek)
                wirdAdapter.notifyDataSetChanged()
            }


        }


    }


    private fun settingUpOnLongClick(database: WirdDatabase) {
        wirdAdapter.onLongClick = { wird ->
            Log.i(TAG, "onViewCreated: long click")
            //set up snackbar to to not delete the wird
            //add dialog to confirm delete
            MaterialAlertDialogBuilder(requireActivity())
                .setMessage(resources.getString(R.string.delete_wird_dialog_message))
                .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                    // Respond to negative button press
                }
                .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                    Snackbar.make(
                        binding.root,
                        "هل أنت متأكد من حذف الورد؟",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("نعم") {

                            Toast.makeText(
                                requireContext(),
                                " تم المسح ",
                                Toast.LENGTH_SHORT
                            ).show()
                            viewmodel.deleteWird(database, wird)
                            wirdAdapter.notifyDataSetChanged()
                        }
                        .show()
                }
                .show()


        }
    }

    private fun settingUpHeader() {
        binding.apply {
            tvDate.text = getCurrentDate()

        }

        //getting name from shared preferences
        val name = requireActivity().getSharedPreferences(PreferenceKeys.nameSharedPref, 0)
            .getString("name", "")

        binding.tvName.text = name
    }

    private fun currentDateIsInList(currentDate: String, doneDays: List<String>): Boolean {
        val newDates = mutableListOf<String>()
        var inList = false
        if (doneDays.contains(currentDate)) {
            Log.i(TAG, "checkIfCurrentDateIsInList: true")
            doneDays.forEach {
                if (it != currentDate) {
                    newDates.add(it)
                    inList = true
                }
            }
        } else {
            Log.i(TAG, "checkIfCurrentDateIsInList: false")
            doneDays.forEach {
                newDates.add(it)
                inList = false
            }
            newDates.add(currentDate)
        }
        Log.i(TAG, "checkIfCurrentDateIsInList: $newDates")

        return inList
    }


    private fun settingUpWirdRv() {
        binding.wirdRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = wirdAdapter

        }


    }

    private fun observeWirds() {
        viewmodel.wirds.observe(viewLifecycleOwner) {
            Log.i(TAG, "observeWirds:item count ${it.size}")
            wirdAdapter.differ.submitList(it)
            if (viewmodel.wirds.value!!.isNotEmpty()) {
                Log.i(TAG, "onViewCreated: ${viewmodel.wirds.value!!.size}")
            } else {
                Log.i(TAG, "onViewCreated: empty")
                binding.emptyAnimation.visibility = View.VISIBLE
            }
        }
    }


}