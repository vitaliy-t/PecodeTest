package com.example.pecodetest.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.pecodetest.databinding.ActivityMainBinding
import com.example.pecodetest.viewModel.CounterViewModel
import dagger.hilt.android.AndroidEntryPoint

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
                        val index: Int = extras.getInt(INTENT_EXTRA_COUNTER_INDEX_KEY)
                        if (index >= 1) {
                            /** As per task:
                             * "clicking on “notification7” will open a fragment with the number 7 and you will be able to swipe between those 7 fragments."
                             * I am able to achieve this, with addition of keeping all fragments and not limiting myself to only those 7 let's say.
                             * However, I am not sure if that is actually what's required, so I will have both implementations just in case.
                             */

                            /**
                             * 1.   Keep the fragment and those that precede it. We basically remove everything after our desired fragment
                             *      and then proceed to that fragment
                             */

                            counterViewModel.removeElementsInRange(index + 1)

                            // Even though ViewPager seems to not allow getting out of range,
                            // I avoid using index in this scenario so as to avoid shooting yourself in a leg
                            viewPager.currentItem = counterViewModel.getCounterListSize() - 1

                            /**
                             * 2.   Keep all fragments, instead, just move the fragment of notification number
                             */

                            // viewPager.currentItem = index
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
        const val INTENT_EXTRA_COUNTER_INDEX_KEY = "intent_extra_counter_index_key"
        const val INTENT_ACTION_COUNTER_NAVIGATE_KEY = "intent_action_counter_navigate_key"
    }
}