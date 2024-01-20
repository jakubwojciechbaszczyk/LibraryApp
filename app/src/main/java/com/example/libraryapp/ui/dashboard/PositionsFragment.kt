package com.example.libraryapp.ui.dashboard

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapp.LibraryAppApplication
import com.example.libraryapp.adapters.PositionAdapter
import com.example.libraryapp.data.position.Position

import com.example.libraryapp.databinding.FragmentPositionsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PositionsFragment : Fragment() {

    private var _binding: FragmentPositionsBinding? = null
    private val viewModel: PositionsViewModel by viewModels() //{
//        PositionViewModelFactory((activity?.application as LibraryAppApplication).positionRepository)
//    }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPositionsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val positionRecyclerView = binding.positionRecycler
        val adapter = PositionAdapter { deletePositionDialog(it) }
        positionRecyclerView.adapter = adapter
        positionRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allPositions.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }

        binding.fab.setOnClickListener {
            PositionDialogFragment().show(childFragmentManager, "PositionDialogFragment")
        }

        return root
    }

    private fun deletePositionDialog(position: Position) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Usunąć pozycje?")

        // Ustawienie przycisku w oknie dialogowym
        builder.setPositiveButton("OK") { dialog, _ ->
            viewModel.delete(position)
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