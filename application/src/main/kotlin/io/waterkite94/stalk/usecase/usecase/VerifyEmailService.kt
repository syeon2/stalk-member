package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.usecase.port.AuthenticationCodePort
import io.waterkite94.stalk.usecase.port.SmtpPort
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

@Service
class VerifyEmailService(
    private val authenticationCodePort: AuthenticationCodePort,
    private val smtpPort: SmtpPort,
    private val env: Environment
) : VerifyEmail {
    private val mailTitle: String = env.getProperty("spring.mail.title") ?: "회원 가입 인증번호입니다."
    private val codeLength: Int = env.getProperty("spring.mail.codeLength")?.toInt() ?: 6

    /**
     * Using the @Async Annotation with sendMailByJavaMailSender
     */
    override fun verifyEmail(email: String): String {
        val authenticationCode = smtpPort.createVerificationCode(codeLength)

        smtpPort.sendMailByJavaMailSender(email, mailTitle, authenticationCode)
        authenticationCodePort.saveAuthenticationCode(email, authenticationCode)

        return email
    }
}
