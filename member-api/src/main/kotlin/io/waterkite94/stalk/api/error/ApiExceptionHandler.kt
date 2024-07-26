package io.waterkite94.stalk.api.error

import io.waterkite94.stalk.api.dto.response.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpServerErrorException.InternalServerError

@RestControllerAdvice
class ApiExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ApiResponse<Unit> =
        ApiResponse.failure(
            exception.bindingResult.allErrors
                .map { error -> error.defaultMessage }
                .toString(),
            HttpStatus.BAD_REQUEST.toString()
        )

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException::class)
    fun handleBadRequestException(exception: RuntimeException): ApiResponse<Unit> =
        ApiResponse.failure(exception.message, HttpStatus.BAD_REQUEST.toString())

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerError::class)
    fun handleInternalServerErrorException(exception: Exception): ApiResponse<Unit> =
        ApiResponse.error(exception.message, HttpStatus.INTERNAL_SERVER_ERROR.toString())
}
