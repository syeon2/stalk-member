package io.waterkite94.stalk.api.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import io.waterkite94.stalk.api.security.util.UserService
import io.waterkite94.stalk.api.security.vo.RequestLogin
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val userService: UserService,
    private val environment: Environment
) : UsernamePasswordAuthenticationFilter(authenticationManager) {
    override fun attemptAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?
    ): Authentication {
        try {
            val creds = ObjectMapper().readValue(request?.inputStream, RequestLogin::class.java)

            return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(creds.email, creds.password, ArrayList())
            )
        } catch (exception: IOException) {
            throw UsernameNotFoundException(exception.message)
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
    }
}
