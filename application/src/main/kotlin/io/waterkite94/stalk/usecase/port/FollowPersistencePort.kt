package io.waterkite94.stalk.usecase.port

interface FollowPersistencePort {
    fun saveFollow(
        followeeId: String,
        followerId: String
    )

    fun deleteFollow(
        followeeId: String,
        followerId: String
    )
}
