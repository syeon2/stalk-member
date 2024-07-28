package io.waterkite94.stalk.api.security.config

import io.waterkite94.stalk.domain.model.Member
import io.waterkite94.stalk.usecase.usecase.UserSecurity
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
@ComponentScan(basePackageClasses = [UserSecurity::class])
class UserService(
    private val userSecurity: UserSecurity
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val findMember =
            userSecurity.findMemberByEmail(username)
                ?: throw UsernameNotFoundException("User $username not found")

        return User(findMember.email, findMember.password, true, true, true, true, ArrayList())
    }

    fun findUserByEmail(email: String): Member {
        val findMember =
            userSecurity.findMemberByEmail(email)
                ?: throw UsernameNotFoundException("User $email not found")

        return findMember
    }
}