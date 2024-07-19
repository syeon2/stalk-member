package io.waterkite94.stalk.usecase.port

import io.waterkite94.stalk.domain.model.Member

interface MemberRepository {
    fun save(member: Member): Member
}
