package io.waterkite94.stalk.api.request

import io.waterkite94.stalk.domain.model.Member

data class CreateMemberRequest(
    val username: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val introduction: String,
    val emailAuthenticationCode: String
) {
    companion object {
        fun toDomain(request: CreateMemberRequest): Member =
            Member(
                username = request.username,
                email = request.email,
                password = request.password,
                phoneNumber = request.phoneNumber,
                introduction = request.introduction
            )
    }
}
