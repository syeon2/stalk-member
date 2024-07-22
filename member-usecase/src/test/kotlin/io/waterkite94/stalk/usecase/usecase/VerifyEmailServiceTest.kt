package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.usecase.IntegrationTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify

class VerifyEmailServiceTest : IntegrationTestSupport() {
    @InjectMocks
    private lateinit var verifyEmailService: VerifyEmailService

    @Test
    fun verifyEmail() {
        // given
        val email = "waterkite94@gmail.com"
        val authenticationCode = "123456"

        val title = "회원 가입 인증번호입니다."
        val codeLength = 6

        doReturn(authenticationCode).`when`(mailUtil).createVerificationCode(codeLength)
        doNothing().`when`(mailUtil).sendMailByJavaMailSender(email, title, authenticationCode)
        doNothing().`when`(authenticationCodePort).saveAuthenticationCode(email, authenticationCode)

        // when
        val toEmail = verifyEmailService.verifyEmail(email)

        // then
        verify(mailUtil).createVerificationCode(codeLength)
        verify(mailUtil).sendMailByJavaMailSender(email, title, authenticationCode)
        verify(authenticationCodePort).saveAuthenticationCode(email, authenticationCode)

        assertThat(toEmail).isEqualTo(email)
    }
}
