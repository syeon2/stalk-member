package io.waterkite94.stalk.api.controller

import io.waterkite94.stalk.api.dto.request.FollowRequest
import io.waterkite94.stalk.api.dto.response.ApiResponse
import io.waterkite94.stalk.api.dto.response.CountFollowResponse
import io.waterkite94.stalk.usecase.usecase.FollowMember
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class FollowRestController(
    private val followMember: FollowMember
) {
    @PostMapping("/follow")
    fun followingApi(
        @RequestBody request: FollowRequest
    ): ApiResponse<String> {
        followMember.following(request.followerId, request.followedId)

        return ApiResponse.success("Following successfully")
    }

    @DeleteMapping("/follow")
    fun unFollowingApi(
        @RequestBody request: FollowRequest
    ): ApiResponse<String> {
        followMember.unfollowing(request.followerId, request.followedId)

        return ApiResponse.success("Unfollowing successfully")
    }

    @GetMapping("/follower/{followerId}")
    fun countFollowerApi(
        @PathVariable followerId: String
    ): ApiResponse<CountFollowResponse> {
        val countFollower = followMember.countFollower(followerId)

        return ApiResponse.success(CountFollowResponse(countFollower))
    }

    @GetMapping("/followed/{followedId}")
    fun countFollowedApi(
        @PathVariable followedId: String
    ): ApiResponse<CountFollowResponse> {
        val countFollowed = followMember.countFollowed(followedId)

        return ApiResponse.success(CountFollowResponse(countFollowed))
    }
}
