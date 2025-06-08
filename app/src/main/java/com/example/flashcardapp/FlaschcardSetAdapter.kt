package com.example.flashcardapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FlashcardSetAdapter(
    private val sets: List<FlashcardSet>,
    private val onSetClicked: (FlashcardSet) -> Unit
) : RecyclerView.Adapter<FlashcardSetAdapter.SetViewHolder>() {

    inner class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textSetName: TextView = itemView.findViewById(R.id.textSetName)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onSetClicked(sets[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flashcard_set, parent, false)
        return SetViewHolder(view)
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        holder.textSetName.text = sets[position].name
    }

    override fun getItemCount() = sets.size
}
