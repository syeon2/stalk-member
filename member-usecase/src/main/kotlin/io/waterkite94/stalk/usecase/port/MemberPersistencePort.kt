package io.waterkite94.stalk.usecase.port

import io.waterkite94.stalk.domain.model.Member

interface MemberPersistencePort {
    fun save(member: Member): Member

    fun findMemberByEmailOrPhoneNumber(
        email: String,
        phoneNumber: String
    ): Member?
}
