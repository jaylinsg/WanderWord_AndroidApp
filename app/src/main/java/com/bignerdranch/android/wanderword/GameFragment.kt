package com.bignerdranch.android.wanderword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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
            // Update the hidden word display based on the user's input
            updateHiddenWordDisplay(userInputText)
            // Decrease the remaining tries
            remainingTries--
            showToast("Incorrect! Tries remaining: $remainingTries")

            // Check if the user has run out of attempts
            if (remainingTries == 0) {
                showToast("You lost, but good try! Come back soon to try again.")
                // Optionally, reset the game for a new word
                generateRandomWord()
                remainingTries = 10
                updateHiddenWordDisplay()
            }
        }

        // Clear the user input after each guess
        userInput.text?.clear()
    }

    // TODO: IMPLEMENT A FAILURE MESSAGE AND FRAGMENT
    private fun showCongratulationsMessage() {
        // Replace the fragment with a CongratulationsFragment
        val congratulationsFragment = CongratulationsFragment()

        // Use FragmentManager to replace the current fragment with the CongratulationsFragment
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.gameFragmentContainer, congratulationsFragment)
            .commit()
    }

    private fun updateHiddenWordDisplay(userInputText: String? = null) {
        // Update the hidden word display based on the user's input
        val updatedHiddenWord = StringBuilder()

        for (i in correctWord.indices) {
            val currentChar = correctWord[i]
            val userInputChar = userInputText?.getOrNull(i)

            if (userInputChar == null) {
                // User hasn't guessed this position yet
                updatedHiddenWord.append("_ ")
            } else if (userInputChar.equals(currentChar, ignoreCase = true)) {
                // User guessed the correct letter
                updatedHiddenWord.append(" $currentChar ")
            } else {
                // User guessed a letter, but it's in the wrong place
                updatedHiddenWord.append("□ ")
            }
        }

        hiddenWordTextView.text = updatedHiddenWord.toString()
    }

    private fun showInstructionsPopup() {
        // Implement the logic to show instructions (e.g., using AlertDialog)
        // You can show a dialog with the game rules and how to play
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
