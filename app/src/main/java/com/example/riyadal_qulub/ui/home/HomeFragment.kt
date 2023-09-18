package com.example.riyadal_qulub.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

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
        val database = WirdDatabase.getDatabase(requireContext())
        viewmodel.getAllWirds(database)

        observeWirds()


        settingUpWirdRv()

        binding.btnAddWird.setOnClickListener {
            findNavController()
                .navigate(R.id.action_homeFragment_to_newWirdFragment)
        }

        wirdAdapter.setOnButtonClickListener {
            if (currentDateIsInList(getCurrentDate(), it.doneDays)) {
                //todo add dialog and change the icon of the button
            } else {
                viewmodel.addDayToDoneDays(database, it.id, getCurrentDate())
                wirdAdapter.notifyDataSetChanged()
            }

            //todo set up some kind of progress indicator and way to redo the wird
        }
        wirdAdapter.onClick = {
            Log.i(TAG, "wird: $it")
            val b = Bundle().apply {
                putParcelable("wird", it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_wirdFragment, b)

        }
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

        wirdAdapter.setOnDayClickListener {
            Log.i(TAG, "Day clicked: ${it.day} ")
        }


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
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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