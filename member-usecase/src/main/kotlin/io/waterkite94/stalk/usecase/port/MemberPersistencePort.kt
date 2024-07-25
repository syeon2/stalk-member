package io.waterkite94.stalk.usecase.port

import io.waterkite94.stalk.domain.model.Member
import io.waterkite94.stalk.domain.model.UpdateMemberInformationDto

interface MemberPersistencePort {
    fun save(member: Member): Member

    fun findMemberByEmailOrPhoneNumber(
        email: String,
        phoneNumber: String
    ): Member?

    fun updateMemberInformation(
        memberId: String,
        memberInformationDto: UpdateMemberInformationDto
    )

    fun updatePassword(
        email: String,
        password: String
    )

    fun findMemberByEmail(email: String): Member?
}
