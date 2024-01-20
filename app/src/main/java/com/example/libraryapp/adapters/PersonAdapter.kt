package com.example.libraryapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.R
import com.example.libraryapp.data.person.Person
import com.example.libraryapp.databinding.PersonItemBinding

class PersonAdapter(private val action: (Person) -> Unit) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    private var items: List<Person> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(person: List<Person>) {
        items = person
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(person: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<PersonItemBinding>(
            LayoutInflater.from(person.context),
            R.layout.person_item,
            person,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { action.invoke(item) }
    }

    override fun getItemCount(): Int = items.size


    class ViewHolder(private val binding: PersonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Person) {
            binding.person = item

            binding.executePendingBindings()
        }
    }
}