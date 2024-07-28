package io.waterkite94.stalk.usecase.usecase

interface FollowMember {
    fun following(
        followeeId: String,
        followerId: String
    )

    fun unfollowing(
        followeeId: String,
        followerId: String
    )
}
