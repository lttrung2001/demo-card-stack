package vn.trunglt.democardstack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import vn.trunglt.democardstack.databinding.ItemCardBinding


class CardAdapter : ListAdapter<Int, CardVH>(object : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return false
    }

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardVH {
        return CardVH(
            ItemCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardVH, position: Int) {
        holder.bind(getItem(position))
    }
}

class CardVH(private val binding: ItemCardBinding) : ViewHolder(binding.root) {
    fun bind(data: Int) {
        binding.root.setImageResource(data)
    }
}