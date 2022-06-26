package com.example.pecodetest.localdatasource

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MySharedPreferences @Inject constructor(@ApplicationContext val context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    private val preferencesEditor: SharedPreferences.Editor = preferences.edit()

    var lastHighestNumber: Int
        get() = preferences.getInt(ID_LAST_HIGH_NUMBER, -1)
        set(number) {
            preferencesEditor.putInt(ID_LAST_HIGH_NUMBER, number).commit()
        }

    companion object {
        private const val fileName: String = "pecodeTestSharedPreferences"
        private const val ID_LAST_HIGH_NUMBER = "last_high_number"
    }
}