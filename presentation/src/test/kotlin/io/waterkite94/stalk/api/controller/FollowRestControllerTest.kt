package io.waterkite94.stalk.api.controller

import io.waterkite94.stalk.api.ControllerTestSupport
import io.waterkite94.stalk.api.dto.request.FollowRequest
import io.waterkite94.stalk.usecase.usecase.FollowMember
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.kotlin.doNothing
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
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
        val followerId = "followerId"
        val followedId = "followedId"
        val request = FollowRequest(followerId, followedId)

        doNothing().`when`(followMember).following(followedId, followedId)

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
        val followerId = "followerId"
        val followedId = "followedId"
        val request = FollowRequest(followerId, followedId)

        doNothing().`when`(followMember).unfollowing(followedId, followedId)

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

    @Test
    @DisplayName(value = "회원이 follow한 회원의 수를 조회하는 API를 호출합니다.")
    @WithMockUser(roles = ["USER"])
    fun countFollowerApi() {
        // given
        val followerId = "followerId"

        given(followMember.countFollower(followerId)).willReturn(1)

        // when  // then
        mockMvc
            .perform(
                get("/api/v1/follower/$followerId")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.count").value(1))
    }

    @Test
    @DisplayName(value = "회원을 follow한 회원의 수를 조회하는 API를 호출합니다.")
    @WithMockUser(roles = ["USER"])
    fun countFollowedApi() {
        // given
        val followedId = "followedId"

        given(followMember.countFollowed(followedId)).willReturn(1)

        // when  // then
        mockMvc
            .perform(
                get("/api/v1/followed/$followedId")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.count").value(1))
    }
}
