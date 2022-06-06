package com.fit.bullsandcows.ui.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    var attempts = MutableLiveData(0)
    var bulls = MutableLiveData("0")
    var cows = MutableLiveData("0")
    var guess = MutableLiveData("")
    var secret = MutableLiveData<String>()
    val adapter = RecordAdapter()
    private val digits = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

    init {
        digits.shuffle()
        val sb = StringBuilder()
        for (i in 0..3) {
            sb.append(digits[i])
        }
        secret.value = sb.toString()
    }

    fun getHint(secret: String, guess: String) {
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
}