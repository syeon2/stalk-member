package io.waterkite94.stalk.usecase.port

interface SmtpPort {
    fun createVerificationCode(codeLength: Int): String

    fun sendMailByJavaMailSender(
        toEmail: String,
        title: String,
        content: String
    )
}
