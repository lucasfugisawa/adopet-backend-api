package br.com.fugisawa.adopetbackendapi.dto

data class UserCreate(
    var email: String,
    var name: String,
    var password: String,
    var about: String,
    var profilePictureUrl: String,
)