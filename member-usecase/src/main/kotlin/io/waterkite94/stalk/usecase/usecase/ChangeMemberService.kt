package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.UpdateMemberInformationDto
import io.waterkite94.stalk.encrypt.util.SecurityUtil
import io.waterkite94.stalk.usecase.port.MemberPersistencePort
import org.springframework.stereotype.Service

@Service
class ChangeMemberService(
    private val memberPersistencePort: MemberPersistencePort,
    private val securityUtil: SecurityUtil
) : ChangeMemberProfile {
    override fun changeMemberProfile(
        memberId: String,
        updateMemberInformationDto: UpdateMemberInformationDto
    ): UpdateMemberInformationDto {
        memberPersistencePort.updateMemberInformation(memberId, updateMemberInformationDto)

        return updateMemberInformationDto
    }

    override fun changeMemberPassword(
        email: String,
        currentPassword: String,
        newPassword: String,
        checkNewPassword: String
    ) {
        validateEmailAndPassword(email, currentPassword)
        validatePasswordsMatch(newPassword, checkNewPassword)

        memberPersistencePort.updatePassword(email, securityUtil.encryptPassword(newPassword))
    }

    override fun changeProfileImageUrl(
        memberId: String,
        profileImageUrl: String
    ) {
        memberPersistencePort.updateProfileImageUrl(memberId, profileImageUrl)
    }

    private fun validatePasswordsMatch(
        newPassword: String,
        checkNewPassword: String
    ) {
        if (newPassword != checkNewPassword) {
            throw RuntimeException("check change passwords must match password")
        }
    }

    private fun validateEmailAndPassword(
        email: String,
        currentPassword: String
    ) {
        val findMember =
            memberPersistencePort.findMemberByEmail(email)
                ?: throw RuntimeException("Unable to find member by email: $email")

        if (!securityUtil.matchesPassword(currentPassword, findMember.password)) {
            throw RuntimeException("Wrong password")
        }
    }
}
