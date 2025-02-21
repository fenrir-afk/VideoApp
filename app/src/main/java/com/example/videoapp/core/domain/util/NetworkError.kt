package com.example.videoapp.core.domain.util

enum class NetworkError: Error {
    RATE_LIMIT_EXCEEDED,  // 429
    INVALID_REQUEST_PARAMS, // 400
    RESOURCE_NOT_FOUND, // 404
    INTERNAL_SERVER_ERROR, //500
    NO_INTERNET,
    SERIALIZATION,
    TOO_MANY_REQUESTS,
    REQUEST_TIMEOUT,
    UNKNOWN,
}