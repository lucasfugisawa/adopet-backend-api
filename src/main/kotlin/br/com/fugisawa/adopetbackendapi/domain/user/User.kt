package br.com.fugisawa.adopetbackendapi.domain.user

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

    @Column(name = "profile_picture_url")
    var profilePictureUrl: String,

    var enabled: Boolean = true,

    @Column(name = "creation_date")
    val creationDate: LocalDateTime? = LocalDateTime.now(),

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
    @JoinTable(
        name = "adopet_user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    val roles: Set<Role>? = null

)