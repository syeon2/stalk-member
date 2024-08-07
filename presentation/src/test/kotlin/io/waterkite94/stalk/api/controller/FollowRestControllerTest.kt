package io.waterkite94.stalk.api.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import io.waterkite94.stalk.api.ControllerTestSupport
import io.waterkite94.stalk.api.dto.request.FollowRequest
import io.waterkite94.stalk.domain.model.FollowInfoDto
import io.waterkite94.stalk.usecase.usecase.FollowMember
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.kotlin.doNothing
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.queryParameters
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
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
        val followId = "followId"
        val followedId = "followedId"
        val request = FollowRequest(followId, followedId)

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
            .andDo(
                document(
                    "follow-create",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("followId").type(JsonFieldType.STRING).description("팔로우 요청한 회원 아이디"),
                        fieldWithPath("followedId").type(JsonFieldType.STRING).description("팔로우 요청받은 회원 아이디")
                    ),
                    responseFields(
                        fieldWithPath("data").type(JsonFieldType.STRING).description("성공 요청 메시지")
                    )
                )
            )
    }

    @Test
    @DisplayName(value = "Unfollowing API를 호출합니다.")
    @WithMockUser(roles = ["USER"])
    fun unfollowingApi() {
        // given
        val followId = "followId"
        val followedId = "followedId"
        val request = FollowRequest(followId, followedId)

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
            .andDo(
                document(
                    "follow-delete",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("followId").type(JsonFieldType.STRING).description("팔로우 취소하는 회원 아이디"),
                        fieldWithPath("followedId").type(JsonFieldType.STRING).description("팔로우 취소받은 회원 아이디")
                    ),
                    responseFields(
                        fieldWithPath("data").type(JsonFieldType.STRING).description("성공 요청 메시지")
                    )
                )
            )
    }

    @Test
    @DisplayName(value = "회원이 follow한 회원의 수를 조회하는 API를 호출합니다.")
    @WithMockUser(roles = ["USER"])
    fun countFollowingApi() {
        // given
        val memberId = "memberId"

        given(followMember.countFollowing(memberId)).willReturn(1)

        // when  // then
        mockMvc
            .perform(
                get("/api/v1/follow/following/count")
                    .queryParam("memberId", memberId)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.count").value(1))
            .andDo(
                document(
                    "followingCount-get",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    queryParameters(
                        parameterWithName("memberId").description("회원 아이디")
                    ),
                    responseFields(
                        fieldWithPath("data.count").description("팔로잉 수")
                    )
                )
            )
    }

    @Test
    @DisplayName(value = "회원을 follow한 회원의 수를 조회하는 API를 호출합니다.")
    @WithMockUser(roles = ["USER"])
    fun countFollowerApi() {
        // given
        val memberId = "memberId"

        given(followMember.countFollower(memberId)).willReturn(1)

        // when  // then
        mockMvc
            .perform(
                get("/api/v1/follow/follower/count")
                    .queryParam("memberId", memberId)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.count").value(1))
            .andDo(
                document(
                    "followerCount-get",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    queryParameters(
                        parameterWithName("memberId").description("회원 아이디")
                    ),
                    responseFields(
                        fieldWithPath("data.count").description("팔로잉 수")
                    )
                )
            )
    }

    @Test
    @DisplayName(value = "회원이 팔로우하는 회원들의 정보를 조회하는 API를 호출합니다.")
    @WithMockUser(roles = ["USER"])
    fun findFollowingsApi() {
        // given
        val memberId = "memberId"
        val offset = 0
        val limit = 10
        val response = followInfoDto()

        given(followMember.findFollowings(memberId, offset, limit)).willReturn(listOf(response))

        // when  // then
        mockMvc
            .perform(
                get("/api/v1/follow/followings")
                    .queryParam("memberId", memberId)
                    .queryParam("offset", offset.toString())
                    .queryParam("limit", limit.toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").isArray)
            .andDo(
                document(
                    "following-get",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    queryParameters(
                        parameterWithName("memberId").description("회원 아이디"),
                        parameterWithName("offset").description("Page Offset"),
                        parameterWithName("limit").description("정보 개수")
                    ),
                    responseFields(
                        fieldWithPath("data[].memberId").type(JsonFieldType.STRING).description("회원 아이디"),
                        fieldWithPath("data[].username").type(JsonFieldType.STRING).description("회원 이름")
                    )
                )
            )
    }

    @Test
    @DisplayName(value = "회원을 팔로우하는 회원들의 정보를 조회하는 API를 호출합니다.")
    @WithMockUser(roles = ["USER"])
    fun findFollowerApi() {
        // given
        val memberId = "memberId"
        val offset = 0
        val limit = 10
        val response = followInfoDto()

        given(followMember.findFollowers(memberId, offset, limit)).willReturn(listOf(response))

        // when  // then
        mockMvc
            .perform(
                get("/api/v1/follow/followers")
                    .queryParam("memberId", memberId)
                    .queryParam("offset", offset.toString())
                    .queryParam("limit", limit.toString())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").isArray)
            .andDo(
                document(
                    "follower-get",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    queryParameters(
                        parameterWithName("memberId").description("회원 아이디"),
                        parameterWithName("offset").description("Page Offset"),
                        parameterWithName("limit").description("정보 개수")
                    ),
                    responseFields(
                        fieldWithPath("data[].memberId").type(JsonFieldType.STRING).description("회원 아이디"),
                        fieldWithPath("data[].username").type(JsonFieldType.STRING).description("회원 이름")
                    )
                )
            )
    }

    private fun followInfoDto() = FollowInfoDto("memberId", "username")
}
