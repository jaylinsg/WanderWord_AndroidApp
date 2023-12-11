package com.bignerdranch.android.wanderword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlin.random.Random

class GameFragment : Fragment() {

    private lateinit var userInput: EditText
    private lateinit var checkButton: Button
    private lateinit var hiddenWordTextView: TextView
    private lateinit var instructionsButton: Button
    private lateinit var wordsList: List<String>
    private lateinit var correctWord: String
    private var remainingTries: Int = 10
    private val guessedWordsList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        userInput = view.findViewById(R.id.userInput)
        checkButton = view.findViewById(R.id.checkButton)
        hiddenWordTextView = view.findViewById(R.id.hiddenWordTextView)
        instructionsButton = view.findViewById(R.id.instructionsButton)

        // Retrieve the selected word from arguments
        arguments?.getString("SELECTED_WORD")?.let {
            correctWord = it
        } ?: run {
            // If no word is passed, generate a random one
            generateRandomWord()
        }

        updateHiddenWordDisplay()

        checkButton.setOnClickListener {
            checkUserInput()
        }

        instructionsButton.setOnClickListener {
            showInstructionsPopup()
        }

        return view
    }

    private fun generateRandomWord() {
        val randomIndex = Random.nextInt(wordsList.size)
        correctWord = wordsList[randomIndex]
    }

    private fun checkUserInput() {
        val userInputText: String = userInput.text.toString()

        // Check if the user input matches the correct word
        if (userInputText.equals(correctWord, ignoreCase = true)) {
            // User won, show congratulations message
            showCongratulationsMessage()
        } else {
            // Add the guessed word to the list
            guessedWordsList.add(userInputText)

            // Update the hidden word display based on the user's input
            updateHiddenWordDisplay()

            // Decrease the remaining tries
            remainingTries--
            showToast("Incorrect! Tries remaining: $remainingTries")

            // Check if the user has run out of attempts
            if (remainingTries == 0) {
                showToast("You lost, but good try! Come back soon to try again.")
                // Optionally, reset the game for a new word
                generateRandomWord()
                remainingTries = 10
                guessedWordsList.clear()
                updateHiddenWordDisplay()
            }
        }

        // Clear the user input after each guess
        userInput.text?.clear()
    }

    // TODO: IMPLEMENT A FAILURE MESSAGE AND FRAGMENT
    //private fun showFailureMessage() {
    private fun showCongratulationsMessage() {
        // Replace the fragment with a CongratulationsFragment
        val congratulationsFragment = CongratulationsFragment()

        // Use FragmentManager to replace the current fragment with the CongratulationsFragment
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.gameFragmentContainer, congratulationsFragment)
            .commit()
    }

    private fun updateHiddenWordDisplay() {
        // Update the hidden word display based on the user's input and guessed words list
        val displayText = StringBuilder()

        // Track discovered letters across all guesses
        val discoveredLetters = mutableSetOf<Char>()

        // Display initial hidden word before any guesses
        for (i in correctWord.indices) {
            val currentChar = correctWord[i]
            if (currentChar.isLetter()) {
                displayText.append("□ ")
            } else {
                displayText.append("$currentChar ")
            }
        }
        displayText.append("\n")

        // Display each guessed word w/ correct letters filled in
        for (guessedWord in guessedWordsList) {
            for (i in correctWord.indices) {
                val currentChar = correctWord[i]
                val guessedChar = guessedWord.getOrNull(i)

                if (guessedChar != null && guessedChar.equals(currentChar, ignoreCase = true)) {
                    // User guessed the correct letter, update the hidden word
                    displayText.append(" $currentChar ")
                    discoveredLetters.add(currentChar.lowercaseChar())
                } else if (currentChar.isLetter() && discoveredLetters.contains(currentChar.lowercaseChar())) {
                    // It's a discovered letter -> show it
                    displayText.append(" $currentChar ")
                } else if (currentChar.isLetter()) {
                    // It's a letter, but not guessed correctly, show placeholder
                    displayText.append("□ ")
                } else {
                    // It's not a letter (e.g., space or punctuation), show as is
                    displayText.append("$currentChar ")
                }
            }
            displayText.append("\n")
        }
        hiddenWordTextView.text = displayText.toString()
    }

    private fun showInstructionsPopup() {
        val instructions = """
        Welcome to the WanderWord game! This is your chance to grow your collection. Here are the instructions:

        1. Uh oh! A hidden word!
        2. You have 10 chances to guess the word.
        3. Click "Check Guess" to submit a guess.
        4. Any letters you guess correctly are shown (when in the correct place).
        5. Guess the word correctly to earn a collection prize!
        6. Lose the game after 10 incorrect guesses.
        7. If you lose, come back tomorrow and try again!
        7. HINT: The location details may help you guess the word. Good luck and have fun!
    """.trimIndent()

        // Create an AlertDialog
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("WanderWord Instructions")
        alertDialogBuilder.setMessage(instructions)

        // Set a positive button and its click listener
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        // Show the AlertDialog
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
