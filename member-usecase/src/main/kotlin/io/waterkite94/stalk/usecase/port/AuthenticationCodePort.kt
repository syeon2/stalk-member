package io.waterkite94.stalk.usecase.port

interface AuthenticationCodePort {
    fun saveAuthenticationCode(
        email: String,
        authenticationCode: String
    )

    fun getAuthenticationCode(email: String): String?
}
