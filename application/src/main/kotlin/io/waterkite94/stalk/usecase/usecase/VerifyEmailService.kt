package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.mail.MailUtil
import io.waterkite94.stalk.usecase.port.AuthenticationCodePort
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

@Service
class VerifyEmailService(
    private val mailUtil: MailUtil,
    private val authenticationCodePort: AuthenticationCodePort,
    private val env: Environment
) : VerifyEmail {
    private val mailTitle: String = env.getProperty("spring.mail.title") ?: "회원 가입 인증번호입니다."
    private val codeLength: Int = env.getProperty("spring.mail.codeLength")?.toInt() ?: 6

    /**
     * Using the @Async Annotation with sendMailByJavaMailSender
     */
    override fun verifyEmail(email: String): String {
        val authenticationCode = mailUtil.createVerificationCode(codeLength)

        mailUtil.sendMailByJavaMailSender(email, mailTitle, authenticationCode)
        authenticationCodePort.saveAuthenticationCode(email, authenticationCode)

        return email
    }
}
