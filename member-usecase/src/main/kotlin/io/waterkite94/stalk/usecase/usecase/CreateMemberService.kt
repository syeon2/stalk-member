package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.Member
import io.waterkite94.stalk.domain.type.RoleLevel
import io.waterkite94.stalk.exception.DuplicatedMemberException
import io.waterkite94.stalk.usecase.port.AuthenticationCodePort
import io.waterkite94.stalk.usecase.port.CreateMemberPort
import io.waterkite94.stalk.usecase.port.FindMemberPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CreateMemberService(
    private val createMemberPort: CreateMemberPort,
    private val findMemberPort: FindMemberPort,
    private val authenticationCodePort: AuthenticationCodePort
) : CreateMember {
    override fun createMember(
        member: Member,
        emailAuthenticationCode: String
    ): Member {
        validateEmailAndPhoneNumber(member.email, member.phoneNumber)
        validateEmailAuthenticationCode(member.email, emailAuthenticationCode)

        return createMemberPort.save(initializeNewMember(member))
    }

    private fun validateEmailAndPhoneNumber(
        email: String,
        phoneNumber: String
    ) {
        findMemberPort.findMemberByEmailOrPhoneNumber(email, phoneNumber)?.let {
            throw DuplicatedMemberException("Email or phone number already exists")
        }
    }

    private fun validateEmailAuthenticationCode(
        email: String,
        emailAuthenticationCode: String
    ) {
        val authenticationCode = authenticationCodePort.getAuthenticationCode(email)

        authenticationCode ?: let {
            throw RuntimeException("Authentication code not found: $email")
        }

        if (authenticationCode != emailAuthenticationCode) {
            throw RuntimeException("Authentication code does not match email: $emailAuthenticationCode")
        }
    }

    private fun initializeNewMember(member: Member): Member =
        member
            .withMemberId(generateMemberId())
            .withPassword(encryptPassword(member.password))
            .withRoleLevel(assignDefaultRole())

    private fun generateMemberId(): String = UUID.randomUUID().toString()

    private fun encryptPassword(password: String): String {
        // TODO:: encrypting password
        return "encryptedPassword"
    }

    private fun assignDefaultRole(): RoleLevel = RoleLevel.USER_GENERAL
}
