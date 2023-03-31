package br.com.fugisawa.adopetbackendapi.domain.pet

import br.com.fugisawa.adopetbackendapi.domain.user.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "pet")
data class Pet(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var name: String,

    @Enumerated(EnumType.STRING)
    var species: PetSpecies,

    @Enumerated(EnumType.STRING)
    var size: PetSize,

    var personality: String,
    var city: String,
    var state: String,

    @ManyToOne
    var owner: User,

    var profilePictureUrl: String,

    @Enumerated(EnumType.STRING)
    var status: PetStatus = PetStatus.AVAILABLE,

    var statusDate: LocalDateTime = LocalDateTime.now(),
    val creationDate: LocalDateTime = LocalDateTime.now(),
)