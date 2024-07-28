package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.Member
import io.waterkite94.stalk.exception.AuthenticationCodeNotFoundException
import io.waterkite94.stalk.exception.DuplicatedMemberException
import io.waterkite94.stalk.exception.InvalidAuthenticationException
import io.waterkite94.stalk.usecase.IntegrationTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify

class CreateMemberServiceTest : IntegrationTestSupport() {
    @InjectMocks
    private lateinit var createMemberService: CreateMemberService

    @Test
    @DisplayName(value = "회원 도메인을 초기화하여 Repository에 전송합니다.")
    fun createMember() {
        // given
        val member = createMemberDomain()
        val authenticationCode = "123456"
        val encryptedPassword = "123456789910"

        given(memberPersistencePort.findMemberByEmailOrPhoneNumber(member.email, member.phoneNumber))
            .willReturn(null)

        given(authenticationCodePort.getAuthenticationCode(member.email))
            .willReturn(authenticationCode)

        given(securityUtil.encryptPassword(member.password))
            .willReturn(encryptedPassword)

        given(memberPersistencePort.saveMember(any())).willAnswer { invocation ->
            invocation.arguments[0] as Member
        }

        // when
        createMemberService.createMember(member, authenticationCode)

        // then
        assertThat(member.id).isEqualTo(member.id)
        val memberCaptor = argumentCaptor<Member>()
        verify(memberPersistencePort).saveMember(memberCaptor.capture())

        val capturedMember = memberCaptor.firstValue

        assertThat(capturedMember)
            .extracting("email", "phoneNumber")
            .containsExactlyInAnyOrder(member.email, member.phoneNumber)

        assertThat(capturedMember.email).isEqualTo(member.email)
        assertThat(capturedMember.phoneNumber).isEqualTo(member.phoneNumber)
        assertThat(capturedMember.password).isEqualTo(encryptedPassword)
        assertThat(capturedMember.memberId).isNotEmpty
    }

    @Test
    @DisplayName("회원의 이메일 또는 전화번호가 중복된 경우 예외를 발생시킵니다.")
    fun `should throw exception if email or phone number is duplicated`() {
        // given
        val member = createMemberDomain()
        given(memberPersistencePort.findMemberByEmailOrPhoneNumber(member.email, member.phoneNumber))
            .willReturn(member)

        // when & then
        assertThatThrownBy {
            createMemberService.createMember(member, "someCode")
        }.isInstanceOf(DuplicatedMemberException::class.java)
            .hasMessage("Email or phone number already exists")
    }

    @Test
    @DisplayName("인증 코드가 존재하지 않으면 예외를 발생시킵니다.")
    fun `should throw exception if authentication code is not found`() {
        // given
        val member = createMemberDomain()
        given(authenticationCodePort.getAuthenticationCode(member.email))
            .willReturn(null)

        // when & then
        assertThatThrownBy {
            createMemberService.createMember(member, "someCode")
        }.isInstanceOf(AuthenticationCodeNotFoundException::class.java)
            .hasMessage("Authentication code not found: ${member.email}")
    }

    @Test
    @DisplayName("인증 코드가 일치하지 않으면 예외를 발생시킵니다.")
    fun `should throw exception if authentication code does not match`() {
        // given
        val member = createMemberDomain()
        val wrongCode = "wrongCode"
        val correctCode = "correctCode"

        given(authenticationCodePort.getAuthenticationCode(member.email))
            .willReturn(correctCode)

        // when & then
        assertThatThrownBy {
            createMemberService.createMember(member, wrongCode)
        }.isInstanceOf(InvalidAuthenticationException::class.java)
            .hasMessage("Authentication code does not match email: $wrongCode")
    }

    private fun createMemberDomain(): Member =
        Member(
            username = "username",
            email = "email",
            password = "password",
            phoneNumber = "phoneNumber",
            introduction = "introduction"
        )
}
