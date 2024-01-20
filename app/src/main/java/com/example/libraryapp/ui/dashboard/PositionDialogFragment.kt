package com.example.libraryapp.ui.dashboard

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.libraryapp.LibraryAppApplication
import com.example.libraryapp.R
import com.example.libraryapp.data.position.Position
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PositionDialogFragment : DialogFragment() {
    private val viewModel: PositionsViewModel by viewModels()// {
//        PositionViewModelFactory((activity?.application as LibraryAppApplication).positionRepository)
//    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.fragment_position_dialog, null)

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setView(view)
            .setTitle("Dodaj Książke")
            .setPositiveButton("Ok") { _, _ ->
                val identity = view.findViewById<EditText>(R.id.editTextIdentity).text.toString()
                val title = view.findViewById<EditText>(R.id.editTextTitle).text.toString()
                val author = view.findViewById<EditText>(R.id.editTextAuthor).text.toString()
                val year = view.findViewById<EditText>(R.id.editTextYear).text.toString().toIntOrNull() ?: 0
                val description = view.findViewById<EditText>(R.id.editTextDescription).text.toString()
                val http = view.findViewById<EditText>(R.id.editTextHttp).text.toString()

                val newPosition = Position(identity, title, author, year, description, http)
                viewModel.insert(newPosition)
            }
            .setNegativeButton("Anuluj") { dialog, _ ->
                dialog.cancel()
            }

        return alertDialogBuilder.create()
    }
}