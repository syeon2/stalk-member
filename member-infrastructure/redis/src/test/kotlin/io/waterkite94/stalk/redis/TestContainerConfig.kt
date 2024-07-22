package io.waterkite94.stalk.redis

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

class TestContainerConfig : BeforeAllCallback {
    companion object {
        private const val REDIS_IMAGE: String = "redis:latest"
        private const val REDIS_PORT: Int = 6379
    }

    private lateinit var redis: GenericContainer<*>

    override fun beforeAll(p0: ExtensionContext?) {
        redis =
            GenericContainer(DockerImageName.parse(REDIS_IMAGE))
                .withExposedPorts(REDIS_PORT)

        redis.start()

        System.setProperty("spring.data.redis.host", redis.host)
        System.setProperty("spring.data.redis.port", redis.getMappedPort(REDIS_PORT).toString())
    }
}
