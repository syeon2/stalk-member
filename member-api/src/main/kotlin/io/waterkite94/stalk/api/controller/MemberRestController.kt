package io.waterkite94.stalk.api.controller

import io.waterkite94.stalk.api.dto.request.CreateMemberRequest
import io.waterkite94.stalk.api.dto.response.ApiResponse
import io.waterkite94.stalk.api.dto.response.CreateMemberResponse
import io.waterkite94.stalk.api.dto.response.VerifyEmailResponse
import io.waterkite94.stalk.usecase.usecase.CreateMember
import io.waterkite94.stalk.usecase.usecase.VerifyEmail
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class MemberRestController(
    private val createMember: CreateMember,
    private val verifyEmail: VerifyEmail
) {
    @PostMapping("/members")
    fun createMemberApi(
        @Valid @RequestBody request: CreateMemberRequest
    ): ApiResponse<CreateMemberResponse> {
        val createdMember =
            createMember.createMember(request.toDomain(), request.emailAuthenticationCode)

        return ApiResponse.success(CreateMemberResponse.toResponse(createdMember))
    }

    @PostMapping("/member/verification-email/{email}")
    fun verifyEmailApi(
        @PathVariable email: String
    ): ApiResponse<VerifyEmailResponse> {
        val fromEmail = verifyEmail.verifyEmail(email)

        return ApiResponse.success(VerifyEmailResponse(fromEmail))
    }
}
