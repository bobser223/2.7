package com.example.dice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DiceViewModel : ViewModel() {
    private val model = DiceModel()
    private val currentDiceValues = MutableList(5) { 1 }

    private val _diceValues = MutableLiveData<List<Int>>(currentDiceValues.toList())
    val diceValues: LiveData<List<Int>> get() = _diceValues

    private val _isRolling = MutableLiveData(false)
    val isRolling: LiveData<Boolean> get() = _isRolling

    private val _buttonText = MutableLiveData(ROLL_TEXT)
    val buttonText: LiveData<String> get() = _buttonText

    private var rollJob: Job? = null

    fun rollDice() {
        rollJob?.cancel()

        _isRolling.value = true
        _buttonText.value = RESTART_TEXT

        rollJob = viewModelScope.launch {
            model.rollDice { index, number ->
                currentDiceValues[index] = number
                _diceValues.value = currentDiceValues.toList()
            }

            _isRolling.value = false
            _buttonText.value = ROLL_TEXT
        }
    }

    private companion object {
        const val ROLL_TEXT = "ROLL"
        const val RESTART_TEXT = "RESTART"
    }
}
