package io.waterkite94.stalk.api.controller

import io.waterkite94.stalk.api.ControllerTestSupport
import io.waterkite94.stalk.api.request.CreateMemberRequest
import io.waterkite94.stalk.domain.model.Member
import io.waterkite94.stalk.domain.type.RoleLevel
import io.waterkite94.stalk.usecase.usecase.CreateMember
import io.waterkite94.stalk.usecase.usecase.VerifyEmail
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
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

    @Autowired
    private lateinit var memberRestController: MemberRestController

    @Test
    @WithMockUser(roles = ["USER"])
    fun createMemberRequest() {
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
    fun verifyEmailRequest() {
        // given
        val email = "test@test.com"

        given(verifyEmail.verifyEmail(email)).willReturn(email)

        // when   // then
        mockMvc
            .perform(
                post("/api/v1/member/verification-email/$email")
                    .with(csrf())
                    .param("email", email)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
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
            LocalDateTime.now(),
            LocalDateTime.now()
        )
}
