package com.tiago.blockchain.model.vo

sealed class ApiError {
    object ConnectionError : ApiError()
    object NotFoundError : ApiError()
    object InternalError : ApiError()
    object ExceptionNotMapped : ApiError()
}
