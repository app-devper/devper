package com.devper.app.core.design.extension

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

const val SERVER_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss[.SSS]'Z'"
const val SERVER_DATE_PATTERN = "yyyy-MM-dd"

const val PRESENT_DATE_PATTERN = "dd/MM/yyyy"
const val PRESENT_TIME_PATTERN = "HH:mm"
const val PRESENT_DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm"

const val DATE_TIME_PICTURE_NAME_FORMAT = "yyyyMMddHHmmss"


fun String.formatDate(src: String = SERVER_DATE_TIME_PATTERN, dest: String = PRESENT_DATE_PATTERN): String {
    return toDate(src)?.format(dest) ?: this
}

fun String.toServerDate(): String {
    return toDate(PRESENT_DATE_PATTERN)?.format(SERVER_DATE_PATTERN) ?: this
}

@OptIn(FormatStringsInDatetimeFormats::class)
fun LocalDateTime.format(dest: String): String {
    val format = LocalDateTime.Format {
        byUnicodePattern(dest)
    }
    return this.format(format)
}

@OptIn(FormatStringsInDatetimeFormats::class)
fun String.toDate(src: String): LocalDateTime? {
    return try {
        val format = LocalDateTime.Format {
            byUnicodePattern(src)
        }
        LocalDateTime.parse(input = this, format = format)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}