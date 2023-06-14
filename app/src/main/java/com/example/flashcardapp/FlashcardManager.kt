package com.example.flashcardapp

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

object FlashcardManager {
    private const val FLASHCARD_PREFS = "flashcard_prefs"
    private const val FLASHCARD_KEY = "flashcard_list"

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var gson: Gson

    private var flashcards: MutableList<Flashcard> = mutableListOf()

    private var isInitialized = false

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(FLASHCARD_PREFS, Context.MODE_PRIVATE)
        gson = Gson()

        // Retrieve saved flashcards
        val flashcardJson = sharedPreferences.getString(FLASHCARD_KEY, null)
        flashcardJson?.let {
            flashcards = gson.fromJson(it, Array<Flashcard>::class.java).toMutableList()
        }

        isInitialized = true
    }

    fun getFlashcards(): List<Flashcard> {
        checkInitialized()
        return flashcards
    }

    fun addFlashcard(flashcard: Flashcard) {
        checkInitialized()
        flashcards.add(flashcard)
        saveFlashcards()
    }

    fun saveFlashcards() {
        checkInitialized()
        val flashcardJson = gson.toJson(flashcards)
        sharedPreferences.edit().putString(FLASHCARD_KEY, flashcardJson).apply()
    }
    fun removeFlashcards(flashcard: Flashcard) {
        checkInitialized()
        flashcards.remove(flashcard)
        saveFlashcards()
    }

    private fun checkInitialized() {
        if (!isInitialized) {
            throw UninitializedPropertyAccessException("FlashcardManager has not been initialized. Call init() before using other functions.")
        }
    }
}
