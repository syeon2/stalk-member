package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.Member
import io.waterkite94.stalk.usecase.IntegrationTestSupport
import org.assertj.core.api.Assertions.assertThat
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

        given(memberPersistencePort.findMemberByEmailOrPhoneNumber(member.email, member.phoneNumber))
            .willReturn(null)

        given(authenticationCodePort.getAuthenticationCode(member.email))
            .willReturn(authenticationCode)

        given(securityUtil.encryptPassword(member.password))
            .willReturn("123456789910")

        given(memberPersistencePort.saveMember(any())).willAnswer { invocation ->
            invocation.arguments[0] as Member
        }

        // when
        createMemberService.createMember(member, authenticationCode)

        // then
        val memberCaptor = argumentCaptor<Member>()
        verify(memberPersistencePort).saveMember(memberCaptor.capture())

        val capturedMember = memberCaptor.firstValue

        assertThat(capturedMember)
            .extracting("email", "phoneNumber")
            .containsExactlyInAnyOrder(member.email, member.phoneNumber)

        assertThat(capturedMember.password).isNotEqualTo(member.password)
        assertThat(capturedMember.memberId).isNotEmpty()
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
