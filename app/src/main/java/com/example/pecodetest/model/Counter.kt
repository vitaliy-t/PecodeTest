package com.example.pecodetest.model

data class Counter(
    val number: Int,
    val notificationIds: MutableList<Int> = mutableListOf()
)
