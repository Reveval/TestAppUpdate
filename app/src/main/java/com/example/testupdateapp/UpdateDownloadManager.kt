package com.example.testupdateapp

enum class UpdateDownloadManager(val value: Int) {
    OKHTTP(1),
    ANDROID_DOWNLOAD_MANAGER(2),
    FETCH(3);
    companion object {
        fun fromInt(value: Int) = entries.firstOrNull { it.value == value } ?: FETCH
    }
}