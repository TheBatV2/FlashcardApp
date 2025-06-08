package com.example.flashcardapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val flashcardSets = mutableListOf<FlashcardSet>() // Holds the flashcard sets

    private val createSetLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val setName = data?.getStringExtra("setName")
            val questions = data?.getStringArrayListExtra("questions")
            val answers = data?.getStringArrayListExtra("answers")

            if (setName != null && questions != null && answers != null) {
                val newSet = FlashcardSet(setName, questions, answers)
                flashcardSets.add(newSet)
                Toast.makeText(this, "Set \"$setName\" added with ${questions.size} cards!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add permanent default sets here:
        addDefaultFlashcardSets()

        val buttonCreateSet = findViewById<Button>(R.id.buttonCreateSet)
        val buttonSelectSet = findViewById<Button>(R.id.buttonSelectSet)

        buttonCreateSet.setOnClickListener {
            openCreateSet()
        }

        buttonSelectSet.setOnClickListener {
            if (flashcardSets.isEmpty()) {
                Toast.makeText(this, "No flashcard sets available. Please create one first.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showSetSelectionDialog()
        }
    }

    private fun openCreateSet() {
        val intent = Intent(this, CreateSetActivity::class.java)
        createSetLauncher.launch(intent)
    }

    private fun showSetSelectionDialog() {
        val setNames = flashcardSets.map { it.name }.toTypedArray()

        AlertDialog.Builder(this)
            .setTitle("Select Flashcard Set")
            .setItems(setNames) { _, which ->
                val selectedSet = flashcardSets[which]
                openStudyActivity(selectedSet)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun openStudyActivity(flashcardSet: FlashcardSet) {
        val intent = Intent(this, StudyActivity::class.java)
        intent.putExtra("setName", flashcardSet.name)
        intent.putStringArrayListExtra("questions", ArrayList(flashcardSet.questions))
        intent.putStringArrayListExtra("answers", ArrayList(flashcardSet.answers))
        startActivity(intent)
    }

    private fun addDefaultFlashcardSets() {
        val stateCapitals = FlashcardSet(

            "State Capitals",
            arrayListOf(
                "What is the capital of Alabama?",
                "What is the capital of Alaska?",
                "What is the capital of Arizona?"
            ),
            arrayListOf("Montgomery", "Juneau", "Phoenix")
        )// Example default set: US State Capitals

        val simpleMath = FlashcardSet(
            "Simple Math",
            arrayListOf("What is 2+2?", "What is 3*5?", "What is 10-4?", "What is 6/2?"),
            arrayListOf("4", "15", "6", "3")
        )
        // Example default set: Simple Math

        flashcardSets.add(stateCapitals)
        flashcardSets.add(simpleMath)
    }
}

// Data class to hold flashcard set info
data class FlashcardSet(
    val name: String,
    val questions: List<String>,
    val answers: List<String>
)
