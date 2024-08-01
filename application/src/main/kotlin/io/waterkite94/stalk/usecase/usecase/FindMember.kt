package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.Member

interface FindMember {
    fun findMemberByMemberId(memberId: String): Member
}
