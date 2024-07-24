package io.waterkite94.stalk.api.security.config

import io.waterkite94.stalk.api.security.filter.AuthenticationFilter
import io.waterkite94.stalk.api.security.util.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@ComponentScan("io.waterkite94.stalk")
@EnableWebSecurity
class WebConfig(
    private val passwordEncoder: PasswordEncoder,
    private val userService: UserService,
    private val environment: Environment
) {
    @Bean
    protected fun configure(http: HttpSecurity): SecurityFilterChain {
        val authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder)

        val authenticationManager = authenticationManagerBuilder.build()

        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { authRequest ->
                authRequest
                    .requestMatchers(AntPathRequestMatcher("/api/v1/members"))
                    .permitAll()
                    .requestMatchers(AntPathRequestMatcher("/api/v1/member/verification-email/**"))
                    .permitAll()
                    .requestMatchers(AntPathRequestMatcher("/api/v1/members/**"))
                    .permitAll()
                    .requestMatchers(AntPathRequestMatcher("/**"))
                    .permitAll()
            }.authenticationManager(authenticationManager)
            .sessionManagement { session ->
                session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }.addFilter(getAuthenticationFilter(authenticationManager))
            .headers { headers -> headers.frameOptions { frameOptions -> frameOptions.sameOrigin() } }

        return http.build()
    }

    private fun getAuthenticationFilter(authenticationManager: AuthenticationManager): AuthenticationFilter =
        AuthenticationFilter(authenticationManager, userService, environment)
}
