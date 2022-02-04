package com.nanoshkin.fishermanspocket.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nanoshkin.fishermanspocket.databinding.ItemLureBinding
import com.nanoshkin.fishermanspocket.domain.models.Lure

class LureListAdapter() :
    ListAdapter<Lure, LureListAdapter.LureViewHolder>(LureDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LureViewHolder {
        val binding = ItemLureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LureViewHolder, position: Int) {
        val lure = getItem(position)
        holder.bind(lure)
    }

    class LureViewHolder(
        private val binding: ItemLureBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(lure: Lure) {
            with(binding) {
                nameTextView.text = lure.name
                typeTextView.text = lure.type.toString()
                divingDepthTextView.text = lure.divingDepth
                floatationTextView.text = lure.floatation
                weightTextView.text = lure.weight.toString()
                lengthTextView.text = lure.length.toString()
                caughtFishCountTextView.text = lure.effectiveness.toString()
            }
        }
    }

    private object LureDiffCallback : DiffUtil.ItemCallback<Lure>() {
        override fun areItemsTheSame(
            oldItem: Lure,
            newItem: Lure
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Lure,
            newItem: Lure
        ): Boolean {
            return oldItem == newItem
        }
    }
}