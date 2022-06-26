package com.example.pecodetest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.pecodetest.model.Counter
import com.example.pecodetest.viewModel.CounterViewModel
import com.example.pecodetest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class CounterActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var binding: ActivityMainBinding

    private val counterViewModel: CounterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeViewPager(binding)

        intent.action?.let { action ->
            when (action) {
                INTENT_ACTION_COUNTER_NAVIGATE_KEY -> {
                    intent.extras?.let { extras ->
                        val counter: Int = extras.getInt(INTENT_EXTRA_COUNTER_KEY)
                        if (counter >= 2) {
                            // In this particular case, index will always be -1 of the value
                            viewPager.currentItem = intent.extras?.getInt(INTENT_EXTRA_COUNTER_KEY)!! - 1
                        }
                    }
                }
                else -> null
            }
        }
    }

    private fun initializeViewPager(binding: ActivityMainBinding) {
        viewPager = binding.vpMainPager
        viewPager.adapter = CounterViewAdapter(counterViewModel)
    }

    companion object {
        const val INTENT_EXTRA_COUNTER_KEY = "intent_extra_counter_key"
        const val INTENT_ACTION_COUNTER_NAVIGATE_KEY = "intent_action_counter_navigate_key"
    }
}