package io.waterkite94.stalk.api.dto.response

import io.waterkite94.stalk.domain.model.Member
import io.waterkite94.stalk.domain.vo.RoleLevel

data class CreateMemberResponse(
    val memberId: String,
    val username: String,
    val email: String,
    val phoneNumber: String,
    val roleLevel: RoleLevel
) {
    companion object {
        fun toResponse(member: Member): CreateMemberResponse =
            CreateMemberResponse(
                memberId = member.memberId ?: "null",
                username = member.username,
                email = member.email,
                phoneNumber = member.phoneNumber,
                roleLevel = member.roleLevel ?: RoleLevel.ADMIN_NORMAL
            )
    }
}
