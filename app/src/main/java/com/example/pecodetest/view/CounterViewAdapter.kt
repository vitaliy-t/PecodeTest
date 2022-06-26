package com.example.pecodetest.view

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pecodetest.model.Counter
import com.example.pecodetest.viewModel.CounterViewModel
import com.example.pecodetest.util.NotificationUtil
import com.example.pecodetest.databinding.FragmentCounterBinding
import kotlin.random.Random

class CounterViewAdapter(
    private val counterViewModel: CounterViewModel
) : RecyclerView.Adapter<CounterViewAdapter.CounterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounterViewHolder {
        return CounterViewHolder(FragmentCounterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CounterViewHolder, position: Int) {
        holder.bind(counterViewModel.counters.value!![position], position)
    }

    override fun getItemCount(): Int {
        return counterViewModel.getCounterListSize()
    }

    inner class CounterViewHolder internal constructor(private val binding: FragmentCounterBinding) : RecyclerView.ViewHolder(binding.root) {
        internal fun bind(counter: Counter, position: Int) {
            binding.tvFragmentNumber.text = counter.number.toString()
            setBtMinusVisibility(position)
            setBtMinusClick()
            setBtPlusClick()
            setTvCreateNotificationClick(position)
        }

        private fun setBtMinusVisibility(position: Int) {
            if (position == 0) {
                binding.btMinus.visibility = View.GONE
            } else {
                binding.btMinus.visibility = View.VISIBLE
            }
        }

        private fun setBtMinusClick() = binding.btMinus.setOnClickListener {
            counterViewModel.cancelLastCounterNotifications()
            counterViewModel.removeLastCounter()
            notifyItemRemoved(counterViewModel.getCounterListSize())
        }

        private fun setBtPlusClick() = binding.btPlus.setOnClickListener {
            counterViewModel.addNewCounter()
            notifyItemInserted(counterViewModel.getCounterListSize() - 1)
        }

        private fun setTvCreateNotificationClick(position: Int) = binding.tvCreateNotification.setOnClickListener {
            val intent: Intent = Intent(binding.root.context, CounterActivity::class.java)
                .putExtra(CounterActivity.INTENT_EXTRA_COUNTER_KEY, counterViewModel.counters.value!![position].number)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .setAction(CounterActivity.INTENT_ACTION_COUNTER_NAVIGATE_KEY)
            counterViewModel.sendCounterNotification(position, intent)
        }
    }
}