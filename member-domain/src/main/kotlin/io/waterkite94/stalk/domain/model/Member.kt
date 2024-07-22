package io.waterkite94.stalk.domain.model

import io.waterkite94.stalk.domain.type.RoleLevel
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
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
) {
    fun withMemberId(memberId: String): Member = copy(memberId = memberId)

    fun withPassword(password: String): Member = copy(password = password)

    fun withRoleLevel(roleLevel: RoleLevel): Member = copy(roleLevel = roleLevel)
}
