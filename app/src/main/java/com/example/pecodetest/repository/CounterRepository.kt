package com.example.pecodetest.repository

import com.example.pecodetest.localdatasource.MySharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CounterRepository @Inject constructor(
    private val sharedPreferences: MySharedPreferences
) {
    fun saveLastHighestNumber(number: Int) {
        sharedPreferences.lastHighestNumber = number
    }

    fun getLastHighestNumber() = if (sharedPreferences.lastHighestNumber == -1) 1 else sharedPreferences.lastHighestNumber
}