package com.example.flashcardapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class StudyActivity : AppCompatActivity() {

    private lateinit var setName: String
    private var questions: List<String> = emptyList()
    private var answers: List<String> = emptyList()

    private var currentIndex = 0
    private var showingAnswer = false

    private lateinit var textSetName: TextView
    private lateinit var textScore: TextView
    private lateinit var textFlashcard: TextView
    private lateinit var buttonShowAnswer: Button
    private lateinit var buttonNext: Button
    private lateinit var buttonPrevious: Button


    private var allSets: List<FlashcardSet> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)

        // Intent data (optional fallback set)
        setName = intent.getStringExtra("setName") ?: "Flashcard Set"
        questions = intent.getStringArrayListExtra("questions") ?: emptyList()
        answers = intent.getStringArrayListExtra("answers") ?: emptyList()

        // Find views
        textSetName = findViewById(R.id.textSetName)
        textScore = findViewById(R.id.textScore)
        textFlashcard = findViewById(R.id.textFlashcard)
        buttonShowAnswer = findViewById(R.id.buttonShowAnswer)
        buttonNext = findViewById(R.id.buttonNext)
        buttonPrevious = findViewById(R.id.buttonPrevious)


        // Load all sets (you'll replace this with real data later)
        allSets = loadFlashcardSets()


        // Button behavior
        buttonShowAnswer.setOnClickListener {
            toggleAnswer()
        }

        buttonNext.setOnClickListener {
            if (currentIndex < questions.size - 1) {
                currentIndex++
                showingAnswer = false
                updateFlashcard()
            }
        }

        buttonPrevious.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                showingAnswer = false
                updateFlashcard()
            }
        }

        updateFlashcard()
    }

    private fun updateFlashcard() {
        if (questions.isNotEmpty() && answers.isNotEmpty()) {
            textFlashcard.text = if (showingAnswer) answers[currentIndex] else questions[currentIndex]
            buttonShowAnswer.text = if (showingAnswer) "Show Question" else "Show Answer"
            textScore.text = "Card ${currentIndex + 1} / ${questions.size}"
        } else {
            textFlashcard.text = "No flashcards available"
            textScore.text = ""
            buttonShowAnswer.text = "Show Answer"
        }
    }

    private fun toggleAnswer() {
        showingAnswer = !showingAnswer
        updateFlashcard()
    }

    // Stub function to simulate flashcard sets
    private fun loadFlashcardSets(): List<FlashcardSet> {
        // TODO: Replace with persistent storage
        val sampleSet = FlashcardSet(
            "Default Set",
            listOf("What is 2+2?", "Capital of France?"),
            listOf("4", "Paris")
        )

        // Include the one passed from MainActivity if available
        val passedSet = if (questions.isNotEmpty() && answers.isNotEmpty()) {
            FlashcardSet(setName, questions, answers)
        } else null

        return if (passedSet != null) listOf(passedSet, sampleSet) else listOf(sampleSet)
    }
}
