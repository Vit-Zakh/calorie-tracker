package com.example.calorietracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calorietracker.databinding.ActivityMainBinding
import com.example.calorietracker.modo.Screens
import com.github.terrakok.modo.android.ModoRender
import com.github.terrakok.modo.android.init
import com.github.terrakok.modo.android.saveState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val modo = MyApplication.modo

    private val modoRender by lazy { ModoRender(this, R.id.container) }

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Theme_CalorieTracker)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        modo.init(savedInstanceState, Screens.DailyIntakeScreen())
    }

    override fun onResume() {
        super.onResume()
        modo.render = modoRender
    }

    override fun onPause() {
        modo.render = null
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        modo.saveState(outState)
    }
}
