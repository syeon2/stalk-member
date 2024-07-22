package io.waterkite94.stalk.redis

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(TestContainerConfig::class)
abstract class IntegrationTestSupport
