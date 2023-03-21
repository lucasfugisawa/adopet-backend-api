package br.com.fugisawa.adopetbackendapi.dto

import br.com.fugisawa.adopetbackendapi.domain.pet.PetSize
import br.com.fugisawa.adopetbackendapi.domain.pet.PetSpecies
import br.com.fugisawa.adopetbackendapi.domain.pet.PetStatus
import java.time.LocalDateTime

data class PetView(
    val id: Long?,
    var name: String,
    var species: PetSpecies,
    var size: PetSize,
    var personality: String,
    var city: String,
    var state: String,
    val owner: UserView,
    var profilePictureUrl: String,
    var status: PetStatus,
    var statusDate: LocalDateTime,
    val creationDate: LocalDateTime?,
)