package com.example.hw5.util

import android.content.Context
import android.content.SharedPreferences

class PrefUtils(context: Context, name: String, mode: Int) {
    companion object {
        private var singleton: PrefUtils? = null
        private lateinit var preferences: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor

        fun with(context: Context, name: String, mode: Int): PrefUtils {
            if (null == singleton) singleton = Builder(context, name, mode).build()
            return singleton as PrefUtils
        }
    }

    init {
        preferences = context.getSharedPreferences(name, mode)
        editor = preferences.edit()
    }

    fun getByKey(key: String): String? {
        return preferences.getString(key, null)
    }

    fun save(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun getAll(): MutableMap<String, *> {
        return preferences.all
    }

    fun removeByKey(key: String) {
        editor.remove(key).apply()
    }
}

private class Builder(val context: Context, val name: String, val mode: Int) {
    fun build(): PrefUtils {
        return PrefUtils(context, name, mode)
    }
}