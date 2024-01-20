package com.example.libraryapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.R
import com.example.libraryapp.data.position.Position
import com.example.libraryapp.databinding.PositionItemBinding

class PositionAdapter(
    private val action: ((Position) -> Unit)? = null
) : RecyclerView.Adapter<PositionAdapter.ViewHolder>() {

    private var items: List<Position> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(position: List<Position>) {
        items = position
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(position: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<PositionItemBinding>(
            LayoutInflater.from(position.context),
            R.layout.position_item,
            position,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { action?.invoke(item) }
    }

    override fun getItemCount(): Int = items.size


    class ViewHolder(private val binding: PositionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Position) {
            binding.position = item

            binding.executePendingBindings()
        }
    }
}