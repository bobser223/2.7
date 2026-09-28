package com.example.multithreading

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.multithreading.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnLoadData.setOnClickListener {
            loadData()
        }

    }

    private fun loadData() {

        Log.d(TAG, "loadData: start data loading from internet...")

        // TODO:

        // on Start:
            // disable button "load data"
            // show progress bar
            // clear City
            // clear Temperature
            // show Toast that data is started loading.

        // on Progress
            // load City
               // and set it into correspondent text view
            // then load temperature for loaded City,
               // and set it into correspondent text view

        // on Finish:
            // hide progress bar
            // enable button "load data"
    }

    companion object {
        val TAG = "XXXX"
    }
}