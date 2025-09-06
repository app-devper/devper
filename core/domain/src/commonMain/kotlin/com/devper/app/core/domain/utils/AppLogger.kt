package com.devper.app.core.domain.utils

import co.touchlab.kermit.Logger

class AppLogger(
    private val tag: String,
    private val isDebug: Boolean = true,
) {
    fun log(msg: String) {
        if (isDebug) {
            printLogD(tag, msg)
        }
    }

    companion object Factory {
        fun buildDebug(tag: String): AppLogger =
            AppLogger(
                tag = tag,
                isDebug = true,
            )

        fun buildRelease(tag: String): AppLogger =
            AppLogger(
                tag = tag,
                isDebug = false,
            )
    }
}

fun printLogD(
    tag: String,
    msg: String,
) {
    Logger.d(msg, null, tag)
}

fun printLogI(
    tag: String,
    msg: String,
) {
    Logger.i(msg, null, tag)
}
