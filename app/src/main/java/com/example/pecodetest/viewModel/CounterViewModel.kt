package com.example.pecodetest.viewModel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pecodetest.model.Counter
import com.example.pecodetest.repository.CounterRepository
import com.example.pecodetest.util.NotificationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CounterViewModel @Inject constructor(
    private val counterRepository: CounterRepository,
    private val notificationUtil: NotificationUtil
) : ViewModel() {
    private val _counters = MutableLiveData<MutableList<Counter>>(mutableListOf())
    val counters: LiveData<MutableList<Counter>>
        get() = _counters

    init {
        val lastHighestNumber = counterRepository.getLastHighestNumber()
        for (number in 1..lastHighestNumber) {
            _counters.value!!.add(Counter(number))
        }
    }

    fun addNewCounter() {
        val lastCounterNumber = _counters.value!!.last().number
        _counters.value!!.add(Counter(lastCounterNumber + 1))
        updateHighestCounter()
    }

    fun removeLastCounter() {
        _counters.value!!.removeLast()
        updateHighestCounter()
    }

    fun getCounterListSize(): Int = _counters.value!!.size

    fun cancelLastCounterNotifications() {
        val lastCounter = _counters.value!!.last()
        lastCounter.notificationIds.forEach {
            notificationUtil.cancelNotification(it)
        }
    }

    fun removeElementsInRange(start: Int, end: Int = getCounterListSize() - 1) {
        for (index in start..end) {
            _counters.value!!.removeLast()
        }
        updateHighestCounter()
    }

    fun sendCounterNotification(position: Int, intent: Intent) {
        val notificationId: Int = Random.nextInt(0, 100000)
        _counters.value!![position].notificationIds.add(notificationId)
        notificationUtil.sendNotificationWithIntent(
            intent,
            "Notification ${_counters.value!![position].number}",
            "Notification ${_counters.value!![position].number}",
            notificationId
        )
    }

    private fun updateHighestCounter() {
        viewModelScope.launch {
            _counters.value?.last()?.let { counter ->
                counterRepository.saveLastHighestNumber(counter.number)
            }
        }
    }
}