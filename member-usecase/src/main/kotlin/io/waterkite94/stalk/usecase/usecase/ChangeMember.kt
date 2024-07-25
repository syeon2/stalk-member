package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.UpdateMemberProfileDto

interface ChangeMember {
    fun changeMemberProfile(
        memberId: String,
        updateMemberProfileDto: UpdateMemberProfileDto
    ): UpdateMemberProfileDto

    fun changeMemberPassword(
        email: String,
        currentPassword: String,
        newPassword: String,
        checkNewPassword: String
    )

    fun changeProfileImageUrl(
        memberId: String,
        profileImageUrl: String
    )

    fun changeStatusInactive(memberId: String)
}
