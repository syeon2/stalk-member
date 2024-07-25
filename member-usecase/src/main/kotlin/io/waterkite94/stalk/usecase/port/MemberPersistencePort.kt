package io.waterkite94.stalk.usecase.port

import io.waterkite94.stalk.domain.model.Member
import io.waterkite94.stalk.domain.model.UpdateMemberProfileDto

interface MemberPersistencePort {
    fun saveMember(member: Member): Member

    fun findMemberByEmailOrPhoneNumber(
        email: String,
        phoneNumber: String
    ): Member?

    fun findMemberByEmail(email: String): Member?

    fun updateMemberProfile(
        memberId: String,
        memberProfileDto: UpdateMemberProfileDto
    )

    fun updatePassword(
        email: String,
        password: String
    )

    fun updateProfileImageUrl(
        memberId: String,
        profileImageUrl: String
    )

    fun updateStatusInactive(memberId: String)
}
