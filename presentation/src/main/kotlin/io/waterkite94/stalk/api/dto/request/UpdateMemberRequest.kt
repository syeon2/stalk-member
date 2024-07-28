package io.waterkite94.stalk.api.dto.request

import io.waterkite94.stalk.domain.model.UpdateMemberProfileDto
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UpdateMemberRequest(
    @field:NotBlank(message = "이름은 빈칸을 허용하지 않습니다.")
    val username: String,
    @field:NotNull(message = "자기소개는 Null을 허용하지 않습니다.")
    val introduction: String
) {
    fun toDto(): UpdateMemberProfileDto =
        UpdateMemberProfileDto(
            username = username,
            introduction = introduction
        )
}
