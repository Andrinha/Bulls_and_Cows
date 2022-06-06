package com.fit.bullsandcows.ui.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.fit.bullsandcows.R
import com.fit.bullsandcows.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private var _binding: ActivityGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        //region Set listeners to buttons
        setNumberTouchListener(binding.button0, 0)
        setNumberTouchListener(binding.button1, 1)
        setNumberTouchListener(binding.button2, 2)
        setNumberTouchListener(binding.button3, 3)
        setNumberTouchListener(binding.button4, 4)
        setNumberTouchListener(binding.button5, 5)
        setNumberTouchListener(binding.button6, 6)
        setNumberTouchListener(binding.button7, 7)
        setNumberTouchListener(binding.button8, 8)
        setNumberTouchListener(binding.button9, 9)
        setClearTouchListener(binding.buttonClear)
        setSubmitTouchListener(binding.buttonSubmit)
        //endregion

        disableButtons()
    }

    // Touch listener for number buttons
    private fun setNumberTouchListener(view: View, number: Int) {
        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val guess = viewModel.guess.value
                    if (guess != null) {
                        if (guess.length < 4) {
                            viewModel.guess.value += number.toString()
                            disableButtons()
                        }
                    }
                }
            }
            v.performClick()
            false
        }
    }

    // Touch listener for clear button
    private fun setClearTouchListener(view: View) {
        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    viewModel.guess.value = ""
                    disableButtons()
                }
            }
            v.performClick()
            false
        }
    }

    // Touch listener for erase button
    private fun setSubmitTouchListener(view: View) {
        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    viewModel.getBullsAndCows(viewModel.secret.value!!, viewModel.guess.value!!)
                    viewModel.guess.value = ""
                    disableButtons()
                }
            }
            v.performClick()
            false
        }
    }

    // Prepare string for the UI
    private fun convertString(value: String): String {
        return when(value.length) {
            0 -> "_ _ _ _"
            1 -> "$value _ _ _"
            2 -> "$value _ _"
            3 -> "$value _"
            4 -> value
            else -> "_ _ _ _"
        }
    }

    // Disable used buttons to prevent repeated digits
    private fun disableButtons() {

        // Enable all buttons
        binding.button0.isEnabled = true
        binding.button1.isEnabled = true
        binding.button2.isEnabled = true
        binding.button3.isEnabled = true
        binding.button4.isEnabled = true
        binding.button5.isEnabled = true
        binding.button6.isEnabled = true
        binding.button7.isEnabled = true
        binding.button8.isEnabled = true
        binding.button9.isEnabled = true
        binding.buttonSubmit.isEnabled = true

        // Disable number buttons that were used in livedata
        for (i in 0 until viewModel.guess.value!!.length) {
            when(viewModel.guess.value!![i]) {
                '0' -> binding.button0.isEnabled = false
                '1' -> binding.button1.isEnabled = false
                '2' -> binding.button2.isEnabled = false
                '3' -> binding.button3.isEnabled = false
                '4' -> binding.button4.isEnabled = false
                '5' -> binding.button5.isEnabled = false
                '6' -> binding.button6.isEnabled = false
                '7' -> binding.button7.isEnabled = false
                '8' -> binding.button8.isEnabled = false
                '9' -> binding.button9.isEnabled = false
            }
        }

        // Disable submit button if guess is not ready
        if (viewModel.guess.value!!.length < 4) {
            binding.buttonSubmit.isEnabled = false
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.guess.observe(this) {
            binding.textInputGuess.text = convertString(it)
        }
        viewModel.bulls.observe(this) {
            binding.textBulls.text = getString(R.string.bulls, it)
        }
        viewModel.cows.observe(this) {
            binding.textCows.text = getString(R.string.cows, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}