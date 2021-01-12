package com.kingmo.example.teamroster.models

data class Response <out T>(val status: Status, val data: T?) {
    enum class Status {
        LOADING,
        SUCCESS,
        ERROR
    }

    companion object {
        fun <T> success(data: T?): Response<T> = Response(Status.SUCCESS, data)
        fun <T> loading(): Response<T> = Response(Status.LOADING, null)
        fun <T> error(): Response<T> = Response(Status.ERROR, null)
    }
}