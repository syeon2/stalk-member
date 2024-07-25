package io.waterkite94.stalk.domain.model

import io.waterkite94.stalk.domain.vo.MemberStatus
import io.waterkite94.stalk.domain.vo.RoleLevel
import java.time.LocalDateTime

data class Member(
    val id: Long? = null,
    var memberId: String? = null,
    val username: String,
    val email: String,
    var password: String,
    val phoneNumber: String,
    val introduction: String,
    val profileImageUrl: String? = null,
    var roleLevel: RoleLevel? = RoleLevel.USER_GENERAL,
    var memberStatus: MemberStatus? = MemberStatus.ACTIVE,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
) {
    fun withMemberId(memberId: String): Member = copy(memberId = memberId)

    fun withPassword(password: String): Member = copy(password = password)

    fun withRoleLevel(roleLevel: RoleLevel): Member = copy(roleLevel = roleLevel)

    fun withMemberStatus(status: MemberStatus): Member = copy(memberStatus = status)
}
