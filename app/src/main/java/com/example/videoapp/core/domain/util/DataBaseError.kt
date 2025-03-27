package com.example.videoapp.core.domain.util

enum class DataBaseError:Error {
    CONSTRAINT_VIOLATION,  // 429
    DISK_IO_ERROR, // 400
    UNKNOWN_DB_ERROR, // 404
    UNKNOWN,
}