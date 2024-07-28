package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.UpdateMemberProfileDto
import io.waterkite94.stalk.domain.model.UpdatePasswordDto
import io.waterkite94.stalk.encrypt.util.SecurityUtil
import io.waterkite94.stalk.exception.InvalidPasswordException
import io.waterkite94.stalk.exception.MemberNotFoundException
import io.waterkite94.stalk.usecase.port.MemberPersistencePort
import org.springframework.stereotype.Service

@Service
class ChangeMemberService(
    private val memberPersistencePort: MemberPersistencePort,
    private val securityUtil: SecurityUtil
) : ChangeMember {
    override fun changeMemberProfile(
        memberId: String,
        updateMemberProfileDto: UpdateMemberProfileDto
    ): UpdateMemberProfileDto {
        memberPersistencePort.updateMemberProfile(memberId, updateMemberProfileDto)

        return updateMemberProfileDto
    }

    override fun changeMemberPassword(updatePasswordDto: UpdatePasswordDto) {
        validateEmailAndPassword(updatePasswordDto.email, updatePasswordDto.currentPassword)
        validatePasswordsMatch(updatePasswordDto.newPassword, updatePasswordDto.checkNewPassword)

        memberPersistencePort.updatePassword(
            updatePasswordDto.email,
            securityUtil.encryptPassword(updatePasswordDto.newPassword)
        )
    }

    override fun changeProfileImageUrl(
        memberId: String,
        profileImageUrl: String
    ) {
        memberPersistencePort.updateProfileImageUrl(memberId, profileImageUrl)
    }

    override fun changeStatusInactive(memberId: String) {
        memberPersistencePort.updateStatusInactive(memberId)
    }

    private fun validatePasswordsMatch(
        newPassword: String,
        checkNewPassword: String
    ) {
        require(newPassword == checkNewPassword) {
            throw InvalidPasswordException("checkNewPassword must match newPassword")
        }
    }

    private fun validateEmailAndPassword(
        email: String,
        currentPassword: String
    ) {
        val findMember =
            memberPersistencePort.findMemberByEmail(email)
                ?: throw MemberNotFoundException("Member not found: $email")

        require(securityUtil.matchesPassword(currentPassword, findMember.password)) {
            throw InvalidPasswordException("Password does not match current: $currentPassword")
        }
    }
}
