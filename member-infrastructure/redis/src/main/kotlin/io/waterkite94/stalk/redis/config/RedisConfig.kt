package io.waterkite94.stalk.redis.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig(
    private val env: Environment
) {
    private val host = env.getProperty("spring.data.redis.host", "localhost")
    private val port = env.getProperty("spring.data.redis.port", "6379").toInt()

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> =
        RedisTemplate<String, Any>().apply {
            this.connectionFactory = connectionFactory
            this.keySerializer = StringRedisSerializer()
            this.valueSerializer = StringRedisSerializer()
            this.hashKeySerializer = StringRedisSerializer()
            this.hashValueSerializer = StringRedisSerializer()
        }

    @Bean
    fun lettuceConnectionFactory(): RedisConnectionFactory {
        val config = RedisStandaloneConfiguration(host, port)

        return LettuceConnectionFactory(config)
    }
}
