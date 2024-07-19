package io.waterkite94.stalk.domain.model

import java.time.LocalDateTime

data class Member(
    val id: Long,
    val memberId: String,
    val username: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val introduction: String,
    val profileImageUrl: String?,
    val roleLevel: RoleLevel,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
