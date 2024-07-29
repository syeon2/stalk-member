package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.FollowInfoDto

interface FollowMember {
    fun following(
        followingId: String,
        followerId: String
    )

    fun unfollowing(
        followingId: String,
        followerId: String
    )

    fun countFollowing(memberId: String): Int

    fun countFollower(memberId: String): Int

    fun findFollowings(
        memberId: String,
        offset: Int,
        limit: Int
    ): List<FollowInfoDto>

    fun findFollowers(
        memberId: String,
        offset: Int,
        limit: Int
    ): List<FollowInfoDto>
}
