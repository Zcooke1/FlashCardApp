package com.example.flashcardapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcardapp.Flashcard
import com.example.flashcardapp.FlashcardManager

class StudyModeActivity : AppCompatActivity() {
    private lateinit var questionTextView: TextView
    private lateinit var answerTextView: TextView
    private lateinit var flipButton: Button
    private lateinit var nextCardButton: Button
    private lateinit var exitStudyModeButton: Button
    private lateinit var previousCardButton: Button


    private var flashcards: List<Flashcard> = emptyList()
    private var currentCardIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_mode)

        // Initialize UI elements
        questionTextView = findViewById(R.id.questionTextView)
        answerTextView = findViewById(R.id.answerTextView)
        flipButton = findViewById(R.id.flipButton)
        nextCardButton = findViewById(R.id.nextCardButton)
        exitStudyModeButton = findViewById(R.id.exitButton)
        previousCardButton = findViewById(R.id.previousCardButton)


        // Retrieve flashcards from FlashcardManager
        flashcards = FlashcardManager.getFlashcards()

        // Initialize UI with first flashcard
        displayFlashcard(currentCardIndex)

        // Set up click listeners
        flipButton.setOnClickListener {
            toggleFlashcardVisibility()
        }

        nextCardButton.setOnClickListener {
            showNextFlashcard()
        }

        exitStudyModeButton.setOnClickListener {
            finish()
        }
        previousCardButton.setOnClickListener {
            showPreviousFlashcard()
        }

    }

    private fun displayFlashcard(index: Int) {
        val flashcard = flashcards[index]
        questionTextView.text = flashcard.question
        answerTextView.text = flashcard.answer
        // Show question, hide answer
        questionTextView.visibility = View.VISIBLE
        answerTextView.visibility = View.GONE
    }

    private fun toggleFlashcardVisibility() {
        // Toggle visibility of question and answer text views
        if (questionTextView.visibility == View.VISIBLE) {
            questionTextView.visibility = View.INVISIBLE
            answerTextView.visibility = View.VISIBLE
        } else {
            questionTextView.visibility = View.VISIBLE
            answerTextView.visibility = View.INVISIBLE
        }
    }


    private fun showNextFlashcard() {
        currentCardIndex++
        if (currentCardIndex < flashcards.size) {
            displayFlashcard(currentCardIndex)
        } else {
            // Reached the end of the flashcard list, handle accordingly (e.g., show message or wrap around)
        }
    }
    private fun showPreviousFlashcard() {
        currentCardIndex--
        if (currentCardIndex >= 0 && currentCardIndex < flashcards.size) {
            displayFlashcard(currentCardIndex)
        } else {
            // Reached the beginning of the flashcard list, handle accordingly (e.g., show message or wrap around)
        }
    }

}
