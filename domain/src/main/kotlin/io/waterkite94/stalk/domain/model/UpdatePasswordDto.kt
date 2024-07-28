package io.waterkite94.stalk.domain.model

data class UpdatePasswordDto(
    val email: String,
    val currentPassword: String,
    val newPassword: String,
    val checkNewPassword: String
)
