package io.waterkite94.stalk.api.controller

import io.waterkite94.stalk.api.dto.request.FollowRequest
import io.waterkite94.stalk.api.dto.response.ApiResponse
import io.waterkite94.stalk.usecase.usecase.FollowMember
import org.springframework.web.bind.annotation.DeleteMapping
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
        followMember.following(request.followeeId, request.followerId)

        return ApiResponse.success("Following successfully")
    }

    @DeleteMapping("/follow")
    fun unFollowingApi(
        @RequestBody request: FollowRequest
    ): ApiResponse<String> {
        followMember.unfollowing(request.followeeId, request.followerId)

        return ApiResponse.success("Unfollowing successfully")
    }
}
