package com.fit.bullsandcows.ui.game

import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.fit.bullsandcows.R
import com.fit.bullsandcows.data.Attempt
import com.fit.bullsandcows.data.Record
import com.fit.bullsandcows.data.RecordDatabase
import com.fit.bullsandcows.databinding.ActivityGameBinding
import com.fit.bullsandcows.utils.toDateFormat
import com.fit.bullsandcows.utils.toTimeFormat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText


class GameActivity : AppCompatActivity() {

    private var _binding: ActivityGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GameViewModel
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        //Get app preferences from settings
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        when(prefs.getString("restrictions", "no")) {
            "attempt" -> {
                binding.restrictionsLayout.visibility = View.VISIBLE
                viewModel.maxAttempts = prefs.getString("attempts_number", "0")!!.toInt()
            }
            "time" -> {
                binding.restrictionsLayout.visibility = View.VISIBLE
                if (!viewModel.isRunning) {
                    val time = prefs.getString("time", "-1")
                    viewModel.time.value = (time!!.toLong().toTimeFormat())
                }
            }
        }

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

        binding.recyclerRecord.layoutManager = LinearLayoutManager(this)
        binding.recyclerRecord.adapter = viewModel.adapter

        if (viewModel.name.value == null) {
            showAlertWithTextInput()
        }
    }

    // Touch listener for number buttons
    private fun setNumberTouchListener(view: View, number: Int) {
        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (viewModel.guess.value!!.length < 4) {
                        viewModel.guess.value += number.toString()
                        startCountDownTimer()
                    }
                    if (viewModel.startTime.value == null) {
                        viewModel.startTime.value = System.currentTimeMillis()
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
                }
            }
            v.performClick()
            false
        }
    }

    // Touch listener for submit button
    private fun setSubmitTouchListener(view: View) {
        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    viewModel.getHint()
                    viewModel.attempts.value = viewModel.attempts.value!!.inc()
                    viewModel.adapter.addRecord(Attempt(
                            viewModel.attempts.value.toString(),
                            viewModel.bulls.value!!,
                            viewModel.cows.value!!,
                            viewModel.guess.value!!))
                    viewModel.guess.value = ""
                    binding.recyclerRecord.scrollToPosition(viewModel.adapter.itemCount - 1)
                }
            }
            v.performClick()
            false
        }
    }

    private fun startCountDownTimer() {
        if (prefs.getString("restrictions", "no") == "time") {
            val time = prefs.getString("time", "-1")
            if (time != null) {
                viewModel.startCountDownTimer(time.toLong())
            }
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

    private fun setButtonStatus(isEnabled: Boolean) {
        binding.button0.isEnabled = isEnabled
        binding.button1.isEnabled = isEnabled
        binding.button2.isEnabled = isEnabled
        binding.button3.isEnabled = isEnabled
        binding.button4.isEnabled = isEnabled
        binding.button5.isEnabled = isEnabled
        binding.button6.isEnabled = isEnabled
        binding.button7.isEnabled = isEnabled
        binding.button8.isEnabled = isEnabled
        binding.button9.isEnabled = isEnabled
    }

    // Disable number buttons that were used in livedata
    private fun disableUsedButtons() {
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
    }

    private fun showAlertWithTextInput() {
        val input = TextInputEditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.hint = "Name"

        MaterialAlertDialogBuilder(this)
            .setTitle("Set your name")
            .setView(input)
            .setCancelable(false)
            .setPositiveButton("OK") { _, _ ->
                viewModel.name.value = input.text.toString()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
                this.finish()
            }
            .show()
    }

    private fun showRetryAlert(title: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(resources.getString(R.string.start_again_message))
            .setCancelable(false)
            .setNegativeButton(resources.getString(R.string.cancel)) { _, _ ->
                this.finish()
            }
            .setPositiveButton(resources.getString(R.string.start)) { _, _ ->
                viewModel.recreate()
            }
            .show()
    }

    private fun insertDataToDatabase() {
        val name = viewModel.name.value!!
        val time = System.currentTimeMillis() - viewModel.startTime.value!!
        val attempts = viewModel.attempts.value!! + 1

        // Create Record Object
        val record = Record(
            0,
            name,
            time.toTimeFormat(),
            attempts.toString(),
            System.currentTimeMillis().toDateFormat())
        // Add Data to Database
        addRecord(record)
    }

    private fun addRecord(record: Record){
        val db = Room.databaseBuilder(
            applicationContext,
            RecordDatabase::class.java, "record_database"
        ).allowMainThreadQueries().build()

        val recordDao = db.recordDao()

        recordDao.addRecord(record)
    }

    override fun onStart() {
        super.onStart()

        // Attach the observers to the LiveData
        viewModel.guess.observe(this) {
            binding.textInputGuess.text = convertString(it)
            binding.buttonSubmit.isEnabled = it.length >= 4
            if (it.length == 4) {
                setButtonStatus(false)
            } else {
                setButtonStatus(true)
                disableUsedButtons()
            }
        }
        // Observe bulls to check if player wins
        viewModel.bulls.observe(this){
            if (it == "4") {
                showRetryAlert(getString(R.string.win))
                viewModel.timer?.cancel()
                if (!viewModel.isRecordWrite) {
                    insertDataToDatabase()
                    viewModel.isRecordWrite = true
                }
            }
        }
        // Observe chosen restriction values
        when (prefs.getString("restrictions", "no")) {
            "attempt" -> {
                viewModel.attempts.observe(this) {
                    binding.textRestriction.text =
                        getString(R.string.attempts, viewModel.maxAttempts - it)
                    if (it >= viewModel.maxAttempts) {
                        showRetryAlert(getString(R.string.defeat))
                    }
                }
            }
            "time" -> {
                viewModel.time.observe(this) {
                    binding.textRestriction.text = it
                    if (it == "00:00") {
                        showRetryAlert(getString(R.string.defeat))
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}