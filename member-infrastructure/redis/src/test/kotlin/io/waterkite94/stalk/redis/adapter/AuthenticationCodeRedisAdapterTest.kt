package io.waterkite94.stalk.redis.adapter

import io.waterkite94.stalk.redis.IntegrationTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate

class AuthenticationCodeRedisAdapterTest : IntegrationTestSupport() {
    @Autowired
    private lateinit var authenticationCodeRedisAdapter: AuthenticationCodeRedisAdapter

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    @BeforeEach
    fun before() {
        redisTemplate.delete(redisTemplate.keys("*"))
    }

    @Test
    @DisplayName(value = "인증 코드를 저장하고 조회합니다.")
    fun saveAuthenticationCodeAndGetCode() {
        // given
        val email = "waterkite94@gmail.com"
        val authenticationCode = "123456"

        // when
        authenticationCodeRedisAdapter.saveAuthenticationCode(email, authenticationCode)

        // then
        val findAuthenticationCode = authenticationCodeRedisAdapter.getAuthenticationCode(email)

        assertThat(findAuthenticationCode).isEqualTo(authenticationCode)
    }
}
