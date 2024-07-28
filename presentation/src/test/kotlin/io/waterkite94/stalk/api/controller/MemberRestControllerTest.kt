package io.waterkite94.stalk.api.controller

import io.waterkite94.stalk.api.ControllerTestSupport
import io.waterkite94.stalk.api.dto.request.ChangeMemberPasswordRequest
import io.waterkite94.stalk.api.dto.request.CreateMemberRequest
import io.waterkite94.stalk.api.dto.request.UpdateMemberRequest
import io.waterkite94.stalk.domain.model.Member
import io.waterkite94.stalk.domain.type.MemberStatus
import io.waterkite94.stalk.domain.type.RoleLevel
import io.waterkite94.stalk.usecase.usecase.ChangeMember
import io.waterkite94.stalk.usecase.usecase.CreateMember
import io.waterkite94.stalk.usecase.usecase.VerifyEmail
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.given
import org.mockito.kotlin.any
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WebMvcTest(controllers = [MemberRestController::class])
class MemberRestControllerTest : ControllerTestSupport() {
    @MockBean
    private lateinit var createMember: CreateMember

    @MockBean
    private lateinit var verifyEmail: VerifyEmail

    @MockBean
    private lateinit var changeMember: ChangeMember

    @Test
    @WithMockUser(roles = ["USER"])
    fun createMemberApiRequest() {
        // given
        val request = requestDto()
        val resultMember = resultDomain()

        given(createMember.createMember(request.toDomain(), request.emailAuthenticationCode))
            .willReturn(resultMember)

        // when  // then
        mockMvc
            .perform(
                post("/api/v1/members")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.memberId").isString)
            .andExpect(jsonPath("$.data.username").isString)
            .andExpect(jsonPath("$.data.email").isString)
            .andExpect(jsonPath("$.data.phoneNumber").isString)
            .andExpect(jsonPath("$.data.roleLevel").isString)
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun verifyEmailApiRequest() {
        // given
        val email = "test@test.com"

        given(verifyEmail.verifyEmail(email)).willReturn(email)

        // when   // then
        mockMvc
            .perform(
                post("/api/v1/members/verification-email/$email")
                    .with(csrf())
                    .param("email", email)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.toEmail").isString)
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun changeMemberProfileRequest() {
        // given
        val memberId = "waterkite94@gmail.com"
        val request = UpdateMemberRequest("username", "introduction")

        given(changeMember.changeMemberProfile(memberId, request.toDto()))
            .willReturn(request.toDto())

        // when   // then
        mockMvc
            .perform(
                patch("/api/v1/members/$memberId")
                    .with(csrf())
                    .param("memberId", memberId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.username").isString)
            .andExpect(jsonPath("$.data.introduction").isString)
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun changeMemberPasswordApi() {
        // given
        val request = ChangeMemberPasswordRequest("email@email.com", "currentPassword", "newPassword", "newPassword")

        doNothing().`when`(changeMember).changeMemberPassword(any())

        // when   // then
        mockMvc
            .perform(
                patch("/api/v1/members/password")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").isString)
            .andExpect(jsonPath("$.data").value("Password updated successfully"))
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun changeMemberProfileImageUrlApiApi() {
        // given
        val memberId = "memberId"
        val profileImageUrl = "profileImageUrl"

        // when  // then
        mockMvc
            .perform(
                patch("/api/v1/members/$memberId/profile-image")
                    .with(csrf())
                    .param("memberId", memberId)
                    .queryParam("profileImageUrl", profileImageUrl)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data").value("Profile Image Url updated successfully"))
    }

    @Test
    @WithMockUser(roles = ["USER"])
    fun changeMemberStatusInactiveApi() {
        // given
        val memberId = "memberId"

        // when  // then
        mockMvc
            .perform(
                patch("/api/v1/members/$memberId/status/inactive")
                    .with(csrf())
                    .param("memberId", memberId)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data").value("Member status set to inactive"))
    }

    private fun requestDto() =
        CreateMemberRequest(
            "firstName",
            "waterkite94@gmail.com",
            "12345678910",
            "00011112222",
            "introduction",
            "123456"
        )

    private fun resultDomain() =
        Member(
            1L,
            "memberId",
            "username",
            "email",
            "encryptedPassword",
            "00011112222",
            "introduction",
            null,
            RoleLevel.USER_GENERAL,
            MemberStatus.ACTIVE,
            LocalDateTime.now(),
            LocalDateTime.now()
        )
}