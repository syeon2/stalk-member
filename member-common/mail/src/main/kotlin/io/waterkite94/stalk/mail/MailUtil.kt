package io.waterkite94.stalk.mail

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.security.SecureRandom

@Component
class MailUtil(
    private val mailSender: JavaMailSender
) {
    private val logger = KotlinLogging.logger {}

    @Async
    fun sendMailByJavaMailSender(
        toEmail: String,
        title: String,
        content: String
    ) {
        try {
            mailSender.send(createEmailForm(toEmail, title, content))
        } catch (exception: Exception) {
            logger.error(exception) { "Failed to send mail. ${exception.message}" }
        }
    }

    fun createVerificationCode(codeLength: Int): String {
        try {
            val random = SecureRandom()
            val builder = StringBuilder()

            for (i in 0 until codeLength) {
                builder.append(random.nextInt(10))
            }

            return builder.toString()
        } catch (e: RuntimeException) {
            throw RuntimeException("Email Exception originate from MailUtil Component")
        }
    }

    private fun createEmailForm(
        toEmail: String,
        title: String,
        content: String
    ) = SimpleMailMessage().also {
        it.setTo(toEmail)
        it.subject = title
        it.text = content
    }
}
