package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.usecase.port.FollowPersistencePort
import org.springframework.stereotype.Service

@Service
class FollowMemberService(
    private val followPersistencePort: FollowPersistencePort
) : FollowMember {
    override fun following(
        followeeId: String,
        followerId: String
    ) {
        followPersistencePort.saveFollow(followeeId, followerId)
    }

    override fun unfollowing(
        followeeId: String,
        followerId: String
    ) {
        followPersistencePort.deleteFollow(followeeId, followerId)
    }
}
