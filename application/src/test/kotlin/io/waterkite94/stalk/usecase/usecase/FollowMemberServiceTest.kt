package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.usecase.IntegrationTestSupport
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class FollowMemberServiceTest : IntegrationTestSupport() {
    @InjectMocks
    private lateinit var followMemberService: FollowMemberService

    @Test
    @DisplayName(value = "follwee 회원이 follower 회원을 following합니다.")
    fun following() {
        // given
        val followeeId = "followeeId"
        val followerId = "followerId"

        doNothing().`when`(followPersistencePort).saveFollow(followeeId, followerId)

        // when
        followMemberService.following(followeeId, followerId)

        // then
        verify(followPersistencePort, times(1)).saveFollow(followeeId, followerId)
    }
}
