package br.com.fugisawa.adopetbackendapi.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "adopet_user")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var email: String,
    var name: String,
    var password: String,
    var about: String,
    var profilePictureUrl: String,
    var enabled: Boolean = true,
    val creationDate: LocalDateTime? = LocalDateTime.now(),
)