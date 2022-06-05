package com.fit.bullsandcows.ui.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    var inputString = MutableLiveData<String>("")

}