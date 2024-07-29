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
        val followId = "followId"
        val followedId = "followedId"

        doNothing().`when`(followPersistencePort).saveFollow(followId, followedId)

        // when
        followMemberService.following(followId, followedId)

        // then
        verify(followPersistencePort, times(1)).saveFollow(followId, followedId)
    }

    @Test
    @DisplayName(value = "follower 회원이 followed 회원을 unfollowing합니다.")
    fun unfollowing() {
        // given
        val followId = "followId"
        val followedId = "followedId"

        doNothing().`when`(followPersistencePort).deleteFollow(followId, followedId)

        // when
        followMemberService.unfollowing(followId, followedId)

        // then
        verify(followPersistencePort, times(1)).deleteFollow(followId, followedId)
    }

    @Test
    @DisplayName(value = "회원이 팔로우하는 회원 수를 조회합니다.")
    fun countFollower() {
        // given
        val memberId = "memberId"

        given(followPersistencePort.countFollowing(memberId)).willReturn(1)

        // when
        val countFollower = followMemberService.countFollowing(memberId)

        // then
        assertThat(countFollower).isEqualTo(1)
    }

    @Test
    @DisplayName(value = "회원을 팔로우하는 회원 수를 조회합니다.")
    fun countFollowed() {
        // given
        val memberId = "memberId"

        given(followPersistencePort.countFollower(memberId)).willReturn(1)

        // when
        val countFollowed = followMemberService.countFollower(memberId)

        // then
        assertThat(countFollowed).isEqualTo(1)
    }

    @Test
    @DisplayName(value = "회원이 팔로우하는 회원 정보들을 조회합니다.")
    fun findFollowings() {
        // given
        val memberId = "memberId"
        val offset = 0
        val limit = 10

        given(followPersistencePort.findFollowings(memberId, offset, limit)).willReturn(listOf())

        // when
        followMemberService.findFollowings(memberId, offset, limit)

        // then
        verify(followPersistencePort, times(1)).findFollowings(memberId, offset, limit)
    }

    @Test
    @DisplayName(value = "회원을 팔로우하는 회원 정보들을 조회합니다.")
    fun findFollowers() {
        // given
        val memberId = "memberId"
        val offset = 0
        val limit = 10

        given(followPersistencePort.findFollowers(memberId, offset, limit)).willReturn(listOf())

        // when
        followMemberService.findFollowers(memberId, offset, limit)

        // then
        verify(followPersistencePort, times(1)).findFollowers(memberId, offset, limit)
    }
}
