package io.waterkite94.stalk.api.security.vo

import com.fasterxml.jackson.annotation.JsonProperty

data class RequestLogin(
    @JsonProperty("email")
    val email: String,
    @JsonProperty("password")
    val password: String
)
