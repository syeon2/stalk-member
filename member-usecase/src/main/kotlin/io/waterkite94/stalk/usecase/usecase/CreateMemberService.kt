package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.Member
import io.waterkite94.stalk.usecase.port.MemberRepository
import org.springframework.stereotype.Service

@Service
class CreateMemberService(
    private val memberRepository: MemberRepository
) : CreateMember {
    override fun createMember(member: Member): Member = memberRepository.save(member)
}
