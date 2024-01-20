package com.example.libraryapp.ui.rent

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapp.adapters.RentAdapter
import com.example.libraryapp.data.rent.Rent
import com.example.libraryapp.databinding.FragmentRentsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentsFragment : Fragment() {

    private var _binding: FragmentRentsBinding? = null
    private val viewModel: RentsViewModel by viewModels() //{
//        RentsViewModelFactory((activity?.application as LibraryAppApplication).rentalRepository)
//    }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRentsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val positionRecyclerView = binding.rentRecycler
        val adapter = RentAdapter { if (it.status) returnRentDialog(it) }

        positionRecyclerView.adapter = adapter
        positionRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.rents.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }

        return root
    }

    private fun returnRentDialog(rent: Rent) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Zwrócić pozycje?")

        // Ustawienie przycisku w oknie dialogowym
        builder.setPositiveButton("OK") { dialog, _ ->
            viewModel.editRent(rent)
            dialog.dismiss()
        }

        builder.setNegativeButton("Anuluj") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}