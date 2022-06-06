package com.fit.bullsandcows.ui.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    var guess = MutableLiveData("")
    var bulls = MutableLiveData("0")
    var cows = MutableLiveData("0")
    var secret = MutableLiveData<String>()
    private val digits = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

    init {
        digits.shuffle()
        val sb = StringBuilder()
        for (i in 0..3) {
            sb.append(digits[i])
        }
        secret.value = sb.toString()
    }

    fun getBullsAndCows(secret: String, guess: String) {
        if (guess.length != 4)
            return

        val h = IntArray(10)
        var bulls = 0
        var cows = 0
        val n = guess.length
        for (idx in 0 until n) {
            val s = secret[idx]
            val g = guess[idx]
            if (s == g) {
                bulls++
            } else {
                if (h[s - '0'] < 0) cows++
                if (h[g - '0'] > 0) cows++
                h[s - '0']++
                h[g - '0']--
            }
        }

        this.bulls.value = bulls.toString()
        this.cows.value = cows.toString()
    }
}