package io.waterkite94.stalk.api.controller

import io.waterkite94.stalk.api.dto.request.FollowRequest
import io.waterkite94.stalk.api.dto.response.ApiResponse
import io.waterkite94.stalk.api.dto.response.CountFollowResponse
import io.waterkite94.stalk.domain.model.FollowInfoDto
import io.waterkite94.stalk.usecase.usecase.FollowMember
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/follow")
class FollowRestController(
    private val followMember: FollowMember
) {
    @PostMapping
    fun followingApi(
        @RequestBody request: FollowRequest
    ): ApiResponse<String> {
        followMember.following(request.followId, request.followedId)

        return ApiResponse.success("Following successfully")
    }

    @DeleteMapping
    fun unFollowingApi(
        @RequestBody request: FollowRequest
    ): ApiResponse<String> {
        followMember.unfollowing(request.followId, request.followedId)

        return ApiResponse.success("Unfollowing successfully")
    }

    @GetMapping("/following/count")
    fun countFollowingApi(
        @RequestParam memberId: String
    ): ApiResponse<CountFollowResponse> {
        val countFollower = followMember.countFollowing(memberId)

        return ApiResponse.success(CountFollowResponse(countFollower))
    }

    @GetMapping("/follower/count")
    fun countFollowerApi(
        @RequestParam memberId: String
    ): ApiResponse<CountFollowResponse> {
        val countFollowed = followMember.countFollower(memberId)

        return ApiResponse.success(CountFollowResponse(countFollowed))
    }

    /**
     * 회원이 Follow한 회원 정보 조회 API
     */
    @GetMapping("/followings")
    fun findFollowingsApi(
        @RequestParam memberId: String,
        @RequestParam offset: Int,
        @RequestParam limit: Int
    ): ApiResponse<List<FollowInfoDto>> {
        val followings = followMember.findFollowings(memberId, offset, limit)

        return ApiResponse.success(followings)
    }

    /**
     * 회원을 Follow한 회원 정보 조회 API
     */
    @GetMapping("/followers")
    fun findFollowersApi(
        @RequestParam memberId: String,
        @RequestParam offset: Int,
        @RequestParam limit: Int
    ): ApiResponse<List<FollowInfoDto>> {
        val followers = followMember.findFollowers(memberId, offset, limit)

        return ApiResponse.success(followers)
    }
}
