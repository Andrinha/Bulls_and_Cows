package com.fit.bullsandcows.ui.game

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class GameViewModel: ViewModel() {
    var attempts = MutableLiveData(0)
    var bulls = MutableLiveData("0")
    var cows = MutableLiveData("0")
    var guess = MutableLiveData("")
    private var secret = MutableLiveData<String>()
    var time = MutableLiveData("0")
    var startTime = MutableLiveData<Long>()
    var name = MutableLiveData<String>()
    var adapter = AttemptAdapter()
    private val digits = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    var isRunning = false
    var maxAttempts = 0
    var isRecordWrite = false
    var timer: CountDownTimer? = null

    init {
        newSecret()
    }

    fun startCountDownTimer(timeMillis: Long) {
        if (timeMillis <= 0 || isRunning)
            return

        timer = object: CountDownTimer(timeMillis, 17) {

            override fun onTick(millisUntilFinished: Long) {
                time.value = dateFormatter(millisUntilFinished)
                isRunning = true
            }

            override fun onFinish() {
                time.value = dateFormatter(0)
            }
        }.start()
    }

    fun dateFormatter(milliseconds: Long): String {
        return if (milliseconds > 60000)
            SimpleDateFormat("mm:ss:SS", Locale.getDefault()).format(Date(milliseconds)).toString()
        else
            SimpleDateFormat("ss:SS", Locale.getDefault()).format(Date(milliseconds)).toString()
    }

    // Generate random number
    private fun newSecret() {
        digits.shuffle()
        val sb = StringBuilder()
        for (i in 0..3) {
            sb.append(digits[i])
        }
        secret.value = sb.toString()
    }

    fun getHint() {
        val secret = this.secret.value!!
        val guess = this.guess.value!!
        var bulls = 0
        var cows = 0
        val numbers = IntArray(10)
        for (i in secret.indices) {
            if (secret[i] == guess[i]) bulls++ else {
                if (numbers[secret[i] - '0']++ < 0) cows++
                if (numbers[guess[i] - '0']-- > 0) cows++
            }
        }
        this.bulls.value = bulls.toString()
        this.cows.value = cows.toString()
    }

    fun recreate() {
        newSecret()
        adapter.clear()
        attempts.value = 0
        guess.value = ""
        isRunning = false
        isRecordWrite = false
    }
}