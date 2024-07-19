package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.Member

interface CreateMemberUseCase {
    fun createMember(
        member: Member,
        emailAuthenticationCode: String
    ): Member
}
