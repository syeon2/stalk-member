package io.waterkite94.stalk.usecase.usecase

import io.waterkite94.stalk.domain.model.Member

interface UserSecurity {
    fun findMemberByEmail(email: String): Member?
}
