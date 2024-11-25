package com.app.weather.core.core.base

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

/* EncryptedSharedPreferences will be the default method of storing data in shared preferences
 * Normal SharedPreferences will be used when a developer explicitly specify that withoutEncryption
 * operation is needed
 */
@Singleton
class SharedPrefManager @Inject constructor(
    private val context: Context,
) : ISharedPrefManager {

    private val sharePref: SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
    }

    override fun getString(
        key: String,
        defaultValue: String?,

        ): String? {
        return sharePref.getString(key, defaultValue)

    }

    override fun getString(key: String): String? {
        return sharePref.getString(key, null)

    }

    override fun setString(key: String, value: String) {
        sharePref.edit().apply { putString(key, value).apply() }

    }

    override fun getBoolean(
        key: String,
        defaultValue: Boolean,

        ): Boolean {
        return sharePref.getBoolean(key, defaultValue)

    }

    override fun setBoolean(key: String, value: Boolean) {
        sharePref.edit().putBoolean(key, value).apply()

    }

    override fun clear() {
        sharePref.edit().clear().apply()

    }

    override fun saveString(key: String, value: String) {
        setString(key, value)
    }

    override fun getLong(key: String, defaultValue: Long?): Long? {
       return sharePref.getLong(key, defaultValue ?: 0L)

    }


    override fun setLong(key: String, value: Long) {
        return sharePref.edit().putLong(key, value).apply()

    }

    override fun containsKey(key: String): Boolean {
        return sharePref.contains(key)

    }

    override fun clearPref(key: String) {
        return sharePref.edit().remove(key).apply()

    }

    companion object {
        const val SHARED_PREFERENCES_FILE = "app_pref"
    }
}