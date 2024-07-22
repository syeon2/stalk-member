package io.waterkite94.stalk.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class WebSecurity {
    @Bean
    protected fun configure(http: HttpSecurity): SecurityFilterChain {
        http.csrf { csrf -> csrf.disable() }

        http.authorizeHttpRequests { authorization ->
            authorization
                .requestMatchers(AntPathRequestMatcher("/api/v1/members"))
                .permitAll()
                .requestMatchers(AntPathRequestMatcher("/api/v1/member/verification-email/**"))
                .permitAll()
                .anyRequest()
                .authenticated()
        }

        return http.build()
    }
}
