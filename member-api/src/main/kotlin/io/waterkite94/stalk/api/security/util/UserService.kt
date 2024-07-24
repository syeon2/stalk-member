package io.waterkite94.stalk.api.security.util

import io.waterkite94.stalk.usecase.usecase.UserSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class UserService(
    private val userSecurity: UserSecurity
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val findMember =
            userSecurity.findMemberByEmail(username)
                ?: throw UsernameNotFoundException("User $username not found")

        return User(findMember.username, findMember.password, true, true, true, true, ArrayList())
    }
}
