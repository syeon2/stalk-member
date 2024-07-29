package io.waterkite94.stalk.api.dto.request

import jakarta.validation.constraints.NotBlank

data class FollowRequest(
    @field:NotBlank(message = "Follow Id는 빈칸을 허용하지 않습니다.")
    val followId: String,
    @field:NotBlank(message = "Followed Id는 빈칸을 허용하지 않습니다.")
    val followedId: String
)
