package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.Member
import io.waterkite94.stalk.usecase.port.CreateMemberPort
import org.springframework.stereotype.Service

@Service
class CreateMemberUseCaseService(
    private val createMemberPort: CreateMemberPort
) : CreateMemberUseCase {
    override fun createMember(member: Member): Member {
//        memberRepository.save(member)
        val members =
            Member(
                null,
                "good",
                "syueon",
                "waterkite94",
                "hello",
                "00011112222",
                "wow!in troduce",
                null,
                Member.RoleLevel.ADMIN_SUPER
            )

        createMemberPort.save(members)

        return members
    }
}
