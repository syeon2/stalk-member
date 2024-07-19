package io.waterkite94.stalk.api.controller

import io.waterkite94.stalk.api.request.CreateMemberRequest
import io.waterkite94.stalk.domain.model.Member
import io.waterkite94.stalk.usecase.usecase.CreateMemberUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/members")
class MemberRestController(
    private val createMemberUseCase: CreateMemberUseCase
) {
    @PostMapping
    fun createMember(
        @RequestBody request: CreateMemberRequest
    ): ResponseEntity<Member> {
        val createdMember = createMemberUseCase.createMember(CreateMemberRequest.toDomain(request))

        return ResponseEntity.ok(createdMember)
    }
}
