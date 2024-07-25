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
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/members")
class MemberRestController(
    private val createMember: CreateMember,
    private val changeMember: ChangeMember,
    private val verifyEmail: VerifyEmail
) {
    @PostMapping
    fun createMemberApi(
        @Valid @RequestBody request: CreateMemberRequest
    ): ApiResponse<CreateMemberResponse> {
        val createdMember =
            createMember.createMember(request.toDomain(), request.emailAuthenticationCode)

        return ApiResponse.success(CreateMemberResponse.toResponse(createdMember))
    }

    @PostMapping("/verification-email/{email}")
    fun verifyEmailApi(
        @PathVariable email: String
    ): ApiResponse<VerifyEmailResponse> {
        val toEmail = verifyEmail.verifyEmail(email)

        return ApiResponse.success(VerifyEmailResponse(toEmail))
    }

    @PatchMapping("/{memberId}")
    fun changeMemberProfileApi(
        @PathVariable memberId: String,
        @Valid @RequestBody request: UpdateMemberRequest
    ): ApiResponse<UpdateMemberProfileDto> {
        val changedMemberProfile = changeMember.changeMemberProfile(memberId, request.toDto())

        return ApiResponse.success(changedMemberProfile)
    }

    @PatchMapping("/password")
    fun changeMemberPasswordApi(
        @Valid @RequestBody request: ChangeMemberPasswordRequest
    ): ApiResponse<String> {
        changeMember.changeMemberPassword(request.toDto())

        return ApiResponse.success("Password updated successfully")
    }

    @PatchMapping("/{memberId}/profile-image")
    fun changeMemberProfileImageUrlApi(
        @PathVariable memberId: String,
        @RequestParam profileImageUrl: String
    ): ApiResponse<String> {
        changeMember.changeProfileImageUrl(memberId, profileImageUrl)

        return ApiResponse.success("Profile Image Url updated successfully")
    }

    @PatchMapping("/{memberId}/status/inactive")
    fun changeMemberStatusInactiveApi(
        @PathVariable memberId: String
    ): ApiResponse<String> {
        changeMember.changeStatusInactive(memberId)

        return ApiResponse.success("Member status set to inactive")
    }
}
