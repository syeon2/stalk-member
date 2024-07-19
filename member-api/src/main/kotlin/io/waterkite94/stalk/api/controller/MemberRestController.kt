package io.waterkite94.stalk.api.controller

import io.waterkite94.stalk.api.request.CreateMemberRequest
import io.waterkite94.stalk.api.response.ApiResponse
import io.waterkite94.stalk.api.response.CreateMemberResponse
import io.waterkite94.stalk.usecase.usecase.CreateMemberUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class MemberRestController(
    private val createMemberUseCase: CreateMemberUseCase
) {
    @PostMapping("/members")
    fun createMember(
        @Valid @RequestBody request: CreateMemberRequest
    ): ApiResponse<CreateMemberResponse> {
        val createdMember =
            createMemberUseCase.createMember(request.toDomain(), request.emailAuthenticationCode)

        return ApiResponse.success(CreateMemberResponse.toResponse(createdMember))
    }
}
