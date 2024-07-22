package io.waterkite94.stalk.api.request

import io.waterkite94.stalk.domain.model.Member
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length

data class CreateMemberRequest(
    @field:NotBlank(message = "회원 이름은 빈칸을 허용하지 않습니다.")
    val username: String,
    @field:Email(message = "이메일 형식으로 입력해야합니다.")
    @field:NotBlank(message = "이메일은 빈칸을 허용하지 않습니다.")
    val email: String,
    @field:NotBlank(message = "비밀번호는 빈칸을 허용하지 않습니다.")
    @field:Length(min = 8, max = 20, message = "비밀번호는 8 ~ 20자리의 문자입니다.")
    val password: String,
    @field:Pattern(regexp = "[0-9]{10,11}", message = "10 ~ 11자리의 숫자만 입력 가능합니다.")
    @field:NotBlank(message = "전화번호는 빈칸을 허용하지 않습니다.")
    val phoneNumber: String,
    @field:NotNull(message = "자기소개는 Null을 허용하지 않습니다.")
    val introduction: String,
    @field:NotBlank(message = "이메일 인증 코드는 빈칸을 허용하지 않습니다.")
    val emailAuthenticationCode: String
) {
    fun toDomain(): Member =
        Member(
            username = username,
            email = email,
            password = password,
            phoneNumber = phoneNumber,
            introduction = introduction
        )
}
