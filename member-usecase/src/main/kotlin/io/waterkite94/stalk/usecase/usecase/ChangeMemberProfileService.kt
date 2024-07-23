package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.UpdateMemberInformationDto
import io.waterkite94.stalk.usecase.port.MemberPersistencePort
import org.springframework.stereotype.Service

@Service
class ChangeMemberProfileService(
    private val memberPersistencePort: MemberPersistencePort
) : ChangeMemberProfile {
    override fun changeMemberProfile(
        memberId: String,
        updateMemberInformationDto: UpdateMemberInformationDto
    ): UpdateMemberInformationDto {
        memberPersistencePort.updateMemberInformation(memberId, updateMemberInformationDto)

        return updateMemberInformationDto
    }
}
