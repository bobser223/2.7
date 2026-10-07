package com.example.dice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

class DiceViewModel : ViewModel() {
    private val _diceValues = MutableLiveData<List<Int>>(mutableListOf(1,1, 1, 1, 1))

    private val currentDiceValues = MutableList(5) { 1 }
    private val lock = Any()
    private val threads = mutableListOf<Thread>()
    val diceValues: LiveData<List<Int>> get() = _diceValues
    private val _isRolling = MutableLiveData<Boolean>( false)
    val isRolling: LiveData<Boolean> get() = _isRolling

//    fun rollDice(){
//
//        if (_isRolling.value == true) return
//
//
//        _isRolling.value = true
//        thread{
//            for(i in 1..20){
//
//                val numbers = List(5) { (1..6).random() }
//                _diceValues.postValue(numbers)
//                Thread.sleep(100)
//
//            }
//            _isRolling.postValue(false)
//        }
//
//    }

    fun rollDice(){

        if (_isRolling.value == true) return


        _isRolling.value = true




        val remainingThreads = AtomicInteger(5)

        for (i in 0 ..< 5) {
            val diceThread = thread(start = false) {
                rollIDice(i, remainingThreads)
            }

            threads.add(diceThread)
            diceThread.start()

        }



    }

    private fun rollIDice(i:Int, remainingThreads: AtomicInteger){
        val rollingIterations = (15..30).random()

        try {
                repeat(rollingIterations) {

                    Thread.sleep(100)

                    val number = (1..6).random()



                    synchronized(lock) {
                        currentDiceValues[i] = number

                        _diceValues.postValue(
                            currentDiceValues.toList()
                        )
                    }

                }
            } catch (e: InterruptedException){
                // onCleared()
            } finally {
                if (remainingThreads.decrementAndGet() == 0) {
                    _isRolling.postValue(false)
                }
            }


    }

    override fun onCleared() {
        super.onCleared()

        threads.forEach {
            it.interrupt()
        }

        threads.clear()
    }


}