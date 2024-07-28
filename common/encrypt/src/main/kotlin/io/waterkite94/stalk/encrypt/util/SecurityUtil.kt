package io.waterkite94.stalk.encrypt.util

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class SecurityUtil(
    private val passwordEncoder: PasswordEncoder
) {
    fun encryptPassword(password: String): String = passwordEncoder.encode(password)

    fun matchesPassword(
        rowPassword: String,
        encryptedPassword: String
    ): Boolean = passwordEncoder.matches(rowPassword, encryptedPassword)
}
