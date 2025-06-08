package com.example.flashcardapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CreateSetActivity : AppCompatActivity() {

    private val flashcards = mutableListOf<Pair<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_set)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editSetName = findViewById<EditText>(R.id.editSetName)
        val editQuestion = findViewById<EditText>(R.id.editQuestion)
        val editAnswer = findViewById<EditText>(R.id.editAnswer)
        val buttonAddCard = findViewById<Button>(R.id.buttonAddCard)
        val buttonSaveSet = findViewById<Button>(R.id.buttonSaveSet)

        buttonAddCard.setOnClickListener {
            val question = editQuestion.text.toString().trim()
            val answer = editAnswer.text.toString().trim()

            if (question.isEmpty() || answer.isEmpty()) {
                Toast.makeText(this, "Please enter both question and answer", Toast.LENGTH_SHORT).show()
            } else {
                flashcards.add(Pair(question, answer))
                Toast.makeText(this, "Card added!", Toast.LENGTH_SHORT).show()
                editQuestion.text.clear()
                editAnswer.text.clear()
            }
        }

        buttonSaveSet.setOnClickListener {
            val setName = editSetName.text.toString().trim()

            if (setName.isEmpty()) {
                Toast.makeText(this, "Please enter a set name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (flashcards.isEmpty()) {
                Toast.makeText(this, "Add at least one card before saving", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resultIntent = Intent().apply {
                putExtra("setName", setName)
                putStringArrayListExtra("questions", ArrayList(flashcards.map { it.first }))
                putStringArrayListExtra("answers", ArrayList(flashcards.map { it.second }))
            }

            setResult(RESULT_OK, resultIntent)
            finish()

        }
    }
}
