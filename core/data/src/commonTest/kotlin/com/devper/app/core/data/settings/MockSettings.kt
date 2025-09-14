package com.devper.app.core.data.settings

import com.russhwolf.settings.Settings

class MockSettings : Settings {
    private val storage = mutableMapOf<String, Any>()

    override fun clear() {
        storage.clear()
    }

    override fun remove(key: String) {
        storage.remove(key)
    }

    override fun hasKey(key: String): Boolean = storage.containsKey(key)

    override val keys: Set<String> get() = storage.keys.toSet()

    override val size: Int get() = storage.size

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        storage[key] as? Boolean ?: defaultValue

    override fun getBooleanOrNull(key: String): Boolean? = storage[key] as? Boolean

    override fun getDouble(key: String, defaultValue: Double): Double =
        storage[key] as? Double ?: defaultValue

    override fun getDoubleOrNull(key: String): Double? = storage[key] as? Double

    override fun getFloat(key: String, defaultValue: Float): Float =
        storage[key] as? Float ?: defaultValue

    override fun getFloatOrNull(key: String): Float? = storage[key] as? Float

    override fun getInt(key: String, defaultValue: Int): Int =
        storage[key] as? Int ?: defaultValue

    override fun getIntOrNull(key: String): Int? = storage[key] as? Int

    override fun getLong(key: String, defaultValue: Long): Long =
        storage[key] as? Long ?: defaultValue

    override fun getLongOrNull(key: String): Long? = storage[key] as? Long

    override fun getString(key: String, defaultValue: String): String =
        storage[key] as? String ?: defaultValue

    override fun getStringOrNull(key: String): String? = storage[key] as? String

    override fun putBoolean(key: String, value: Boolean) {
        storage[key] = value
    }

    override fun putDouble(key: String, value: Double) {
        storage[key] = value
    }

    override fun putFloat(key: String, value: Float) {
        storage[key] = value
    }

    override fun putInt(key: String, value: Int) {
        storage[key] = value
    }

    override fun putLong(key: String, value: Long) {
        storage[key] = value
    }

    override fun putString(key: String, value: String) {
        storage[key] = value
    }
}
