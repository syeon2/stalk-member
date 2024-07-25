package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.UpdateMemberInformationDto

interface ChangeMemberProfile {
    fun changeMemberProfile(
        memberId: String,
        updateMemberInformationDto: UpdateMemberInformationDto
    ): UpdateMemberInformationDto

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
}
