package io.waterkite94.stalk.api.dto.response

sealed class ApiResponse<T> {
    data class Success<T>(
        val data: T?
    ) : ApiResponse<T>()

    data class Failure<T>(
        val message: String?,
        val status: String
    ) : ApiResponse<T>()

    data class Error<T>(
        val message: String?,
        val status: String
    ) : ApiResponse<T>()

    companion object {
        fun <T> success(data: T?): ApiResponse<T> = Success(data)

        fun <T> failure(
            message: String?,
            status: String
        ): ApiResponse<T> = Failure(message, status)

        fun <T> error(
            message: String?,
            status: String
        ): ApiResponse<T> = Error(message, status)
    }
}
