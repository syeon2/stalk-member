package io.waterkite94.stalk.usecase.port

import io.waterkite94.stalk.domain.model.Member

interface FindMemberPort {
    fun findMemberByEmailOrPhoneNumber(
        email: String,
        phoneNumber: String
    ): Member?
}
