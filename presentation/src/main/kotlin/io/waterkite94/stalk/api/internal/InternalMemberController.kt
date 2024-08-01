package io.waterkite94.stalk.api.internal

import io.waterkite94.stalk.domain.model.Member
import io.waterkite94.stalk.usecase.usecase.FindMember
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class InternalMemberController(
    private val findMember: FindMember
) {
    @GetMapping("/internal/members/{memberId}")
    fun getMember(
        @PathVariable memberId: String
    ): Member = findMember.findMemberByMemberId(memberId)
}
