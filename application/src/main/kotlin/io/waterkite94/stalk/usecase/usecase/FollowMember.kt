package io.waterkite94.stalk.usecase.usecase

interface FollowMember {
    fun following(
        followerId: String,
        followedId: String
    )

    fun unfollowing(
        followerId: String,
        followedId: String
    )

    fun countFollower(memberId: String): Int

    fun countFollowed(memberId: String): Int
}
