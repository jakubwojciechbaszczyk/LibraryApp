package com.example.libraryapp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.LibraryAppApplication
import com.example.libraryapp.R
import com.example.libraryapp.adapters.PositionAdapter
import com.example.libraryapp.data.person.Person
import com.example.libraryapp.data.position.Position
import com.example.libraryapp.databinding.DialogWithRecyclerviewBinding

class RentDialogFragment(private val person: Person): DialogFragment() {
    private val viewModel: PersonsViewModel by viewModels() //{
//        PersonViewModelFactory(
//            (activity?.application as LibraryAppApplication).personRepository,
//            (activity?.application as LibraryAppApplication).positionRepository,
//            (activity?.application as LibraryAppApplication).rentalRepository
//        )
//    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: DialogWithRecyclerviewBinding = DataBindingUtil.inflate(
            inflater,R.layout.dialog_with_recyclerview, container, false
        )
        viewModel.person = person
        binding.personName.text = person.name
        val recyclerView: RecyclerView = binding.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = PositionAdapter { onClickItem(it) }
        recyclerView.adapter = adapter

        viewModel.freePositions.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }

        return binding.root
    }

    private fun onClickItem(position: Position) {
        viewModel.rentPosition(position)
        viewModel.editPosition(position)
        dismiss()
    }
}