package io.waterkite94.stalk.usecase.port

interface FollowPersistencePort {
    fun saveFollow(
        followerId: String,
        followedId: String
    )

    fun deleteFollow(
        followerId: String,
        followedId: String
    )

    // 회원이 팔로우한 회원 수
    fun countFollowed(memberId: String): Int

    // 회원을 팔로우한 회원 수
    fun countFollower(memberId: String): Int
}
