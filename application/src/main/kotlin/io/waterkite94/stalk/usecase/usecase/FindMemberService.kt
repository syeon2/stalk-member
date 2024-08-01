package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.Member
import io.waterkite94.stalk.exception.MemberNotFoundException
import io.waterkite94.stalk.usecase.port.MemberPersistencePort
import org.springframework.stereotype.Service

@Service
class FindMemberService(
    private val memberPersistencePort: MemberPersistencePort
) : FindMember {
    override fun findMemberByMemberId(memberId: String): Member =
        requireNotNull(memberPersistencePort.findMemberByMemberId(memberId)) {
            throw MemberNotFoundException(memberId)
        }
}
