package io.waterkite94.stalk.api.controller

import io.waterkite94.stalk.api.dto.request.ChangeMemberPasswordRequest
import io.waterkite94.stalk.api.dto.request.CreateMemberRequest
import io.waterkite94.stalk.api.dto.request.UpdateMemberRequest
import io.waterkite94.stalk.api.dto.response.ApiResponse
import io.waterkite94.stalk.api.dto.response.CreateMemberResponse
import io.waterkite94.stalk.api.dto.response.VerifyEmailResponse
import io.waterkite94.stalk.domain.model.UpdateMemberProfileDto
import io.waterkite94.stalk.usecase.usecase.ChangeMember
import io.waterkite94.stalk.usecase.usecase.CreateMember
import io.waterkite94.stalk.usecase.usecase.VerifyEmail
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class MemberRestController(
    private val createMember: CreateMember,
    private val changeMember: ChangeMember,
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

    @PostMapping("/member/{memberId}")
    fun changeMemberProfileApi(
        @PathVariable memberId: String,
        @RequestBody request: UpdateMemberRequest
    ): ApiResponse<UpdateMemberProfileDto> {
        val changedMemberProfile = changeMember.changeMemberProfile(memberId, request.toDto())

        return ApiResponse.success(changedMemberProfile)
    }

    @PutMapping("/member/password")
    fun changeMemberPasswordApi(
        @RequestBody request: ChangeMemberPasswordRequest
    ): ApiResponse<String> {
        changeMember.changeMemberPassword(
            request.email,
            request.currentPassword,
            request.newPassword,
            request.checkNewPassword
        )

        return ApiResponse.success("success")
    }

    @PutMapping("/member/{memberId}/profile-image-url/{profileImageUrl}")
    fun changeMemberProfileImageUrlApi(
        @PathVariable memberId: String,
        @PathVariable profileImageUrl: String
    ): ApiResponse<String> {
        changeMember.changeProfileImageUrl(memberId, profileImageUrl)

        return ApiResponse.success("success")
    }

    @PutMapping("/member/{memberId}/inactive")
    fun changeMemberStatusInactiveApi(
        @PathVariable memberId: String
    ): ApiResponse<String> {
        changeMember.changeStatusInactive(memberId)

        return ApiResponse.success("success")
    }
}
