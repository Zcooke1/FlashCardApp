package com.example.flashcardapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class AddFlashcardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_flashcard)

        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            val question = findViewById<TextView>(R.id.questionEditText).text.toString()
            val answer = findViewById<TextView>(R.id.answerEditText).text.toString()

            val flashcard = Flashcard(question, answer)

            FlashcardManager.addFlashcard(flashcard)

            Toast.makeText(this, "Flashcard added successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
