package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.Member
import io.waterkite94.stalk.domain.type.MemberStatus
import io.waterkite94.stalk.domain.type.RoleLevel
import io.waterkite94.stalk.encrypt.util.SecurityUtil
import io.waterkite94.stalk.exception.AuthenticationCodeNotFoundException
import io.waterkite94.stalk.exception.DuplicatedMemberException
import io.waterkite94.stalk.exception.InvalidAuthenticationException
import io.waterkite94.stalk.usecase.port.AuthenticationCodePort
import io.waterkite94.stalk.usecase.port.MemberPersistencePort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CreateMemberService(
    private val memberPersistencePort: MemberPersistencePort,
    private val authenticationCodePort: AuthenticationCodePort,
    private val securityUtil: SecurityUtil
) : CreateMember {
    override fun createMember(
        member: Member,
        emailAuthenticationCode: String
    ): Member {
        validateEmailAndPhoneNumber(member.email, member.phoneNumber)
        validateEmailAuthenticationCode(member.email, emailAuthenticationCode)

        return memberPersistencePort.saveMember(initializeNewMember(member))
    }

    private fun validateEmailAndPhoneNumber(
        email: String,
        phoneNumber: String
    ) {
        memberPersistencePort.findMemberByEmailOrPhoneNumber(email, phoneNumber)?.let {
            throw DuplicatedMemberException("Email or phone number already exists")
        }
    }

    private fun validateEmailAuthenticationCode(
        email: String,
        emailAuthenticationCode: String
    ) {
        val authenticationCode =
            authenticationCodePort.getAuthenticationCode(email)
                ?: throw AuthenticationCodeNotFoundException("Authentication code not found: $email")

        require(authenticationCode == emailAuthenticationCode) {
            throw InvalidAuthenticationException("Authentication code does not match email: $emailAuthenticationCode")
        }
    }

    private fun initializeNewMember(member: Member): Member =
        member
            .withMemberId(generateMemberId())
            .withPassword(encryptPassword(member.password))
            .withRoleLevel(assignDefaultRole())
            .withMemberStatus(MemberStatus.ACTIVE)

    private fun generateMemberId(): String = UUID.randomUUID().toString()

    private fun encryptPassword(password: String): String = securityUtil.encryptPassword(password)

    private fun assignDefaultRole(): RoleLevel = RoleLevel.USER_GENERAL
}
