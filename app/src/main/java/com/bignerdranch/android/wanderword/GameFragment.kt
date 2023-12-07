package com.bignerdranch.android.wanderword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class GameFragment : Fragment() {

    private lateinit var userInput: EditText
    private lateinit var checkButton: Button
    var correctWord: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        userInput = view.findViewById(R.id.userInput)
        checkButton = view.findViewById(R.id.checkButton)

        checkButton.setOnClickListener {
            checkUserInput()
        }

        return view
    }

    private fun checkUserInput() {
        val userInputText: String = userInput.text.toString()

        if (userInputText.equals(correctWord, ignoreCase = true)) {
            showToast("Correct!")
        } else {
            showToast("Incorrect!")
        }
    }
}

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}
