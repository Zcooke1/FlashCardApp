package com.example.flashcardapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.flashcardapp.R
import com.example.flashcardapp.Flashcard
import java.util.*

import android.util.Log


class FlashcardAdapter(private val context: Context) : BaseAdapter() {

    private var flashcards: MutableList<Flashcard> = mutableListOf()
    private var selectedFlashcards: MutableSet<Flashcard> = mutableSetOf()

    fun updateFlashcards(flashcards: List<Flashcard>) {
        this.flashcards.clear()
        this.flashcards.addAll(flashcards)
        notifyDataSetChanged()
        Log.d("FlashcardAdapter", "Flashcards updated")
    }

    fun toggleFlashcardSelection(position: Int) {
        val flashcard = flashcards[position]
        if (selectedFlashcards.contains(flashcard)) {
            selectedFlashcards.remove(flashcard)
        } else {
            selectedFlashcards.add(flashcard)
        }
        notifyDataSetChanged()
    }

    fun getSelectedFlashcards(): List<Flashcard> {
        return selectedFlashcards.toList()
    }

    fun clearSelectedFlashcards() {
        selectedFlashcards.clear()
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return flashcards.size
    }

    override fun getItem(position: Int): Any {
        return flashcards[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_flashcard, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val flashcard = flashcards[position]

        viewHolder.questionTextView.text = flashcard.question
        viewHolder.answerTextView.text = flashcard.answer

        if (selectedFlashcards.contains(flashcard)) {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.selectedFlashcardBackground))
        } else {
            view.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        }

        return view
    }

    private class ViewHolder(view: View) {
        val questionTextView: TextView = view.findViewById(R.id.questionTextView)
        val answerTextView: TextView = view.findViewById(R.id.answerTextView)
    }
}

