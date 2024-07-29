package io.waterkite94.stalk.usecase.port

import io.waterkite94.stalk.domain.model.FollowInfoDto

interface FollowPersistencePort {
    fun saveFollow(
        followId: String,
        followedId: String
    )

    fun deleteFollow(
        followId: String,
        followedId: String
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
