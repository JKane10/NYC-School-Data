package com.jkane.a20220402_joshkane_nycschools.network.models

enum class Status {
    SUCCESS,
    ERROR
}

data class Result<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {
    companion object {
        fun <T> success(data: T): Result<T> = Result(status = Status.SUCCESS, data = data)
        fun <T> error(data: T?, message: String): Result<T> = Result(
            status = Status.ERROR,
            data = data,
            message = message
        )
    }
}