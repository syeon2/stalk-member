package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.FollowInfoDto
import io.waterkite94.stalk.usecase.port.FollowPersistencePort
import org.springframework.stereotype.Service

@Service
class FollowMemberService(
    private val followPersistencePort: FollowPersistencePort
) : FollowMember {
    override fun following(
        followingId: String,
        followerId: String
    ) {
        followPersistencePort.saveFollow(followingId, followerId)
    }

    override fun unfollowing(
        followingId: String,
        followerId: String
    ) {
        followPersistencePort.deleteFollow(followingId, followerId)
    }

    override fun countFollowing(memberId: String): Int = followPersistencePort.countFollowing(memberId)

    override fun countFollower(memberId: String): Int = followPersistencePort.countFollower(memberId)

    override fun findFollowings(
        memberId: String,
        offset: Int,
        limit: Int
    ): List<FollowInfoDto> = followPersistencePort.findFollowings(memberId, offset, limit)

    override fun findFollowers(
        memberId: String,
        offset: Int,
        limit: Int
    ): List<FollowInfoDto> = followPersistencePort.findFollowers(memberId, offset, limit)
}
