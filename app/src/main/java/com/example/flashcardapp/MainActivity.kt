package com.example.flashcardapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var flashcardListView: ListView
    private lateinit var addCardButton: Button
    private lateinit var studyModeButton: Button
    private lateinit var deleteButton: Button
    private lateinit var flashcardAdapter: FlashcardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        flashcardAdapter = FlashcardAdapter(this)
        FlashcardManager.init(this)

        flashcardListView = findViewById(R.id.flashcardListView)
        addCardButton = findViewById(R.id.addCardButton)
        studyModeButton = findViewById(R.id.studyModeButton)
        deleteButton = findViewById(R.id.deleteButton)

        // Set click listeners for buttons
        addCardButton.setOnClickListener {
            val intent = Intent(this, AddFlashcardActivity::class.java)
            startActivity(intent)
        }

        studyModeButton.setOnClickListener {
            val intent = Intent(this, StudyModeActivity::class.java)
            startActivity(intent)
        }

        deleteButton.setOnClickListener {
            val selectedFlashcards = flashcardAdapter.getSelectedFlashcards()
            if (selectedFlashcards.isNotEmpty()) {
                showDeleteConfirmationDialog(selectedFlashcards)
            } else {
                Toast.makeText(
                    this,
                    "Please select at least one flashcard to delete",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Create the flashcard adapter and set it as the adapter for the flashcardListView
        flashcardAdapter = FlashcardAdapter(this)
        flashcardListView.adapter = flashcardAdapter

        // Set click listener for flashcard list item
        flashcardListView.setOnItemClickListener { _, _, position, _ ->
            flashcardAdapter.toggleFlashcardSelection(position)
        }
    }

    override fun onResume() {
        super.onResume()
        // Update the flashcard list when the activity is resumed
        flashcardAdapter.updateFlashcards(FlashcardManager.getFlashcards())
        flashcardAdapter.notifyDataSetChanged()
        FlashcardManager.saveFlashcards()
    }

    private fun showDeleteConfirmationDialog(selectedFlashcards: List<Flashcard>) {
        AlertDialog.Builder(this)
            .setTitle("Delete Flashcards")
            .setMessage("Are you sure you want to delete ${selectedFlashcards.size} flashcard(s)?")
            .setPositiveButton("Delete") { _, _ ->
                selectedFlashcards.forEach { flashcard ->
                    FlashcardManager.removeFlashcards(flashcard)
                }
                flashcardAdapter.updateFlashcards(FlashcardManager.getFlashcards())
                Toast.makeText(this, "Flashcards deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

}