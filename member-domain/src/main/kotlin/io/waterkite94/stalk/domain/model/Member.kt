package io.waterkite94.stalk.domain.model

import java.time.LocalDateTime

data class Member(
    val id: Long? = null,
    val memberId: String? = null,
    val username: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val introduction: String,
    val profileImageUrl: String? = null,
    val roleLevel: RoleLevel? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)
