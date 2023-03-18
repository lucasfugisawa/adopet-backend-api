package br.com.fugisawa.adopetbackendapi.dto

import java.time.LocalDateTime

data class UserView(
    val id: Long?,
    var email: String,
    var name: String,
    var about: String?,
    var profilePictureUrl: String?,
    var enabled: Boolean,
    val creationDate: LocalDateTime?,
)