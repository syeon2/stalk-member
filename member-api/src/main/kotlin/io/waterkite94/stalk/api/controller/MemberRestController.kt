package io.waterkite94.stalk.api.controller

import io.waterkite94.stalk.api.request.CreateMemberRequest
import io.waterkite94.stalk.domain.model.Member
import io.waterkite94.stalk.usecase.usecase.CreateMember
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/members")
class MemberRestController(
    private val createMember: CreateMember
) {
    @PostMapping
    fun createMember(
        @RequestBody request: CreateMemberRequest
    ): ResponseEntity<Member> {
        val createdMember = createMember.createMember(CreateMemberRequest.toDomain(request))

        return ResponseEntity.ok(createdMember)
    }
}
