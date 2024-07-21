package io.waterkite94.stalk.adapter

import io.waterkite94.stalk.usecase.port.AuthenticationCodePort
import org.springframework.core.env.Environment
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class AuthenticationCodeRedisAdapter(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val env: Environment
) : AuthenticationCodePort {
    private val expireTime = env.getProperty("spring.data.redis.authentication-code.expire")?.toLong() ?: 5
    private val prefix: String = "email:code"

    override fun saveAuthenticationCode(
        email: String,
        authenticationCode: String
    ) {
        val key = generatedKey(email)

        redisTemplate.opsForValue().set(key, authenticationCode, Duration.ofMinutes(expireTime))
    }

    override fun getAuthenticationCode(email: String): String? {
        val key = generatedKey(email)

        return redisTemplate.opsForValue().get(key) as String?
    }

    private fun generatedKey(email: String): String {
        val key = "$prefix:$email"
        return key
    }
}
