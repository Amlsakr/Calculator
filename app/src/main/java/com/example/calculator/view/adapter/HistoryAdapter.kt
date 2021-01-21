package com.example.calculator.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.databinding.RecyclerviewItemBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    var historyItems  = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val recyclerItemViewBinding =
            RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(recyclerItemViewBinding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.recyclerVireItemBimding.historyTextView.text = historyItems.get(position)
    }

    override fun getItemCount() = historyItems.size

    inner class HistoryViewHolder(var recyclerVireItemBimding : RecyclerviewItemBinding) : RecyclerView.ViewHolder(recyclerVireItemBimding.root) {


    }
}