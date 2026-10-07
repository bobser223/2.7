package com.example.multithreading

import android.os.Bundle
import android.os.Looper
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import com.example.multithreading.databinding.ActivityMainBinding
import android.os.Handler
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var mainHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoadData.setOnClickListener {
            loadData()
        }
    }

//    private fun loadData() {
//
//        // Імітація важкої роботи (60 секунд)
//        Thread.sleep(60_000)
//
//        binding.tvResult.text = "Дані завантажено!"
//    }


    private fun loadData() {
        // Створюємо та запускаємо фоновий потік у лаконічному Kotlin-стилі
        thread {
            // Імітація важкої роботи у фоні (3 секунди)
            Thread.sleep(3000)

            // ПОМИЛКА! Спроба оновити UI прямо з фонового потоку
//            binding.tvResult.text = "Дані завантажено!"
            mainHandler.post{
                binding.tvResult.text = "Дані завантажено!"
            }

        }
    }
}