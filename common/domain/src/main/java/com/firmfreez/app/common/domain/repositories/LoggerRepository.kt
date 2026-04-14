package com.firmfreez.app.common.domain.repositories

interface LoggerRepository {

    fun init(isDebug: Boolean)

    fun i(message: String)
    fun i(tag: String, message: String)

    fun d(message: String)
    fun d(tag: String, message: String)

    fun w(message: String)
    fun w(tag: String, message: String)

    fun e(message: String)
    fun e(error: Throwable)
    fun e(tag: String, message: String)
    fun e(tag: String, error: Throwable)
    fun e(tag: String, message: String, error: Throwable)
}
