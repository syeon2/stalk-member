package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.usecase.port.FollowPersistencePort
import org.springframework.stereotype.Service

@Service
class FollowMemberService(
    private val followPersistencePort: FollowPersistencePort
) : FollowMember {
    override fun following(
        followerId: String,
        followedId: String
    ) {
        followPersistencePort.saveFollow(followerId, followedId)
    }

    override fun unfollowing(
        followerId: String,
        followedId: String
    ) {
        followPersistencePort.deleteFollow(followerId, followedId)
    }

    override fun countFollower(memberId: String): Int = followPersistencePort.countFollower(memberId)

    override fun countFollowed(memberId: String): Int = followPersistencePort.countFollowed(memberId)
}
