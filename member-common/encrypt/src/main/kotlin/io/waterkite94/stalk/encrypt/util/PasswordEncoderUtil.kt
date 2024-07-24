package io.waterkite94.stalk.encrypt.util

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoderUtil(
    private val passwordEncoder: PasswordEncoder
) {
    fun encryptPassword(password: String): String = passwordEncoder.encode(password)
}
