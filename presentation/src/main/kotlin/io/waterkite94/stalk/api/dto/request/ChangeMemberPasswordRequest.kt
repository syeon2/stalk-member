package io.waterkite94.stalk.api.dto.request

import io.waterkite94.stalk.domain.model.UpdatePasswordDto
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class ChangeMemberPasswordRequest(
    @field:Email(message = "이메일 형식으로 입력해야합니다.")
    @field:NotBlank(message = "이메일은 빈칸을 허용하지 않습니다.")
    val email: String,
    @field:NotBlank(message = "비밀번호는 빈칸을 허용하지 않습니다.")
    val currentPassword: String,
    @field:NotBlank(message = "새로운 비밀번호는 빈칸을 허용하지 않습니다.")
    @field:Length(message = "비밀번호는 8 ~ 20자리의 문자입니다.")
    val newPassword: String,
    @field:NotBlank(message = "새로운 비밀번호 확인 값은 빈칸을 허용하지 않습니다.")
    @field:Length(message = "비밀번호는 8 ~ 20자리의 문자입니다.")
    val checkNewPassword: String
) {
    fun toDto(): UpdatePasswordDto =
        UpdatePasswordDto(
            email = this.email,
            currentPassword = this.currentPassword,
            newPassword = this.newPassword,
            checkNewPassword = this.checkNewPassword
        )
}
