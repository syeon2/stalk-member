package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.UpdateMemberProfileDto
import io.waterkite94.stalk.domain.model.UpdatePasswordDto

interface ChangeMember {
    fun changeMemberProfile(
        memberId: String,
        updateMemberProfileDto: UpdateMemberProfileDto
    ): UpdateMemberProfileDto

    fun changeMemberPassword(updatePasswordDto: UpdatePasswordDto)

    fun changeProfileImageUrl(
        memberId: String,
        profileImageUrl: String
    )

    fun changeStatusInactive(memberId: String)
}
