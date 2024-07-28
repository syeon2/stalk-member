package io.waterkite94.stalk.api.controller

import io.waterkite94.stalk.api.ControllerTestSupport
import io.waterkite94.stalk.api.dto.request.FollowRequest
import io.waterkite94.stalk.usecase.usecase.FollowMember
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doNothing
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [FollowRestController::class])
class FollowRestControllerTest : ControllerTestSupport() {
    @MockBean
    private lateinit var followMember: FollowMember

    @Test
    @DisplayName(value = "Following API를 호출합니다.")
    @WithMockUser(roles = ["USER"])
    fun followingApi() {
        // given
        val followeeId = "followeeId"
        val followerId = "followerId"
        val request = FollowRequest(followeeId, followerId)

        doNothing().`when`(followMember).following(followerId, followerId)

        // when  // then
        mockMvc
            .perform(
                post("/api/v1/follow")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data").value("Following successfully"))
    }

    @Test
    @DisplayName(value = "Unfollowing API를 호출합니다.")
    @WithMockUser(roles = ["USER"])
    fun unfollowingApi() {
        // given
        val followeeId = "followeeId"
        val followerId = "followerId"
        val request = FollowRequest(followeeId, followerId)

        doNothing().`when`(followMember).unfollowing(followerId, followerId)

        // when  // then
        mockMvc
            .perform(
                delete("/api/v1/follow")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data").value("Unfollowing successfully"))
    }
}
