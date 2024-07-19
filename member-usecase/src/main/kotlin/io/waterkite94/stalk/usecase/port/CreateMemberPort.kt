package io.waterkite94.stalk.usecase.port

import io.waterkite94.stalk.domain.model.Member

interface CreateMemberPort {
    fun save(member: Member): Member
}
