package com.example.libraryapp.ui.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapp.adapters.PersonAdapter
import com.example.libraryapp.data.person.Person
import com.example.libraryapp.databinding.FragmentPersonsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class PersonsFragment : Fragment() {
    private var _binding: FragmentPersonsBinding? = null
    private val viewModel: PersonsViewModel by viewModels() //{
//        PersonViewModelFactory(
//            (activity?.application as LibraryAppApplication).personRepository,
//            (activity?.application as LibraryAppApplication).positionRepository,
//            (activity?.application as LibraryAppApplication).rentalRepository
//        )
//    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val personRecyclerView = binding.personRecycler
        val adapter = PersonAdapter { showHireDialog(it) }

        personRecyclerView.adapter = adapter
        personRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.allPersons.observe(viewLifecycleOwner) {item ->
            item?.let {
                adapter.submitList(it)
            }
        }


        return root
    }

    fun showHireDialog(person: Person) {
        RentDialogFragment(person).show(childFragmentManager, "RentDialogFragment")
    }

//    private fun loadData(adapter: PersonAdapter, persons: List<Person>) {
//        // Uruchomienie korutyny na głównym wątku
//        CoroutineScope(Dispatchers.Main).launch {
//            // Symulacja zadania trwającego dłuższy czas
//            withContext(Dispatchers.IO) {
//                adapter.submitList(persons)
//          //  }
//
//            // Ukryj pasek postępu po zakończeniu ładowania danych
//            binding.progressBar.visibility = View.GONE
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}