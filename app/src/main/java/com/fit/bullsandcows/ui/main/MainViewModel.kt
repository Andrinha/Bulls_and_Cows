package com.fit.bullsandcows.ui.main

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // Test
    var test = MutableLiveData<String>()

    init {
        startTimer()
    }

    private fun startTimer() {
        object: CountDownTimer(20000, 1000) {
            override fun onTick(p0: Long) {
                test.value = p0.toString()
            }

            override fun onFinish() {

            }
        }.start()
    }
}