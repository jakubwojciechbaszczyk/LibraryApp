package com.example.libraryapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.R
import com.example.libraryapp.data.rent.Rent
import com.example.libraryapp.databinding.RentalItemBinding
import java.time.format.DateTimeFormatter

class RentAdapter(private val action: (Rent) -> Unit) : RecyclerView.Adapter<RentAdapter.ViewHolder>() {

    private var items: List<Rent> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(position: List<Rent>) {
        items = position
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(position: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<RentalItemBinding>(
            LayoutInflater.from(position.context),
            R.layout.rental_item,
            position,
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
    class ViewHolder(private val binding: RentalItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Rent) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            val person = item.person.name
            val title = item.book.title
            val author = item.book.author
            val takeDate = item.take_date?.format(formatter) ?: "-"
            val planReturnDate = item.plan_return_date?.format(formatter) ?: "-"
            val returnDate = item.return_date?.format(formatter) ?: "-"

            binding.rentPersonName.text = person
            binding.rentBookTitle.text = "Tytuł: $title"
            binding.rentBookAuth.text = "Autor: $author"
            binding.rentPlanReturnDate.text = "Planowana data zwrotu: $planReturnDate"
            binding.rentDate .text = "Data wypożyczenia: $takeDate"
            binding.rentReturnDate.text = "Data zwrotu: $returnDate"
            if (item.status) binding.rentStatus.setText(R.string.rent_label)
            else binding.rentStatus.setText(R.string.return_label)
            binding.executePendingBindings()
        }
    }
}