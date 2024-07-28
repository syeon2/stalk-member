package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.usecase.IntegrationTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class FollowMemberServiceTest : IntegrationTestSupport() {
    @InjectMocks
    private lateinit var followMemberService: FollowMemberService

    @Test
    @DisplayName(value = "follwer 회원이 followed 회원을 following합니다.")
    fun following() {
        // given
        val followerId = "followerId"
        val followedId = "followedId"

        doNothing().`when`(followPersistencePort).saveFollow(followerId, followedId)

        // when
        followMemberService.following(followerId, followedId)

        // then
        verify(followPersistencePort, times(1)).saveFollow(followerId, followedId)
    }

    @Test
    @DisplayName(value = "follower 회원이 followed 회원을 unfollowing합니다.")
    fun unfollowing() {
        // given
        val followerId = "followerId"
        val followedId = "followedId"

        doNothing().`when`(followPersistencePort).deleteFollow(followerId, followedId)

        // when
        followMemberService.unfollowing(followerId, followedId)

        // then
        verify(followPersistencePort, times(1)).deleteFollow(followerId, followedId)
    }

    @Test
    @DisplayName(value = "회원이 팔로우하는 회원 수를 조회합니다.")
    fun countFollower() {
        // given
        val followerId = "followerId"

        given(followPersistencePort.countFollower(followerId)).willReturn(1)

        // when
        val countFollower = followMemberService.countFollower(followerId)

        // then
        assertThat(countFollower).isEqualTo(1)
    }

    @Test
    @DisplayName(value = "회원을 팔로우하는 회원 수를 조회합니다.")
    fun countFollowed() {
        // given
        val followedId = "followedId"

        given(followPersistencePort.countFollowed(followedId)).willReturn(1)

        // when
        val countFollowed = followMemberService.countFollowed(followedId)

        // then
        assertThat(countFollowed).isEqualTo(1)
    }
}
