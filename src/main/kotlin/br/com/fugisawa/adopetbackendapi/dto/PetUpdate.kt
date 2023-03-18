package br.com.fugisawa.adopetbackendapi.dto

import br.com.fugisawa.adopetbackendapi.domain.PetSize
import br.com.fugisawa.adopetbackendapi.domain.PetSpecies
import br.com.fugisawa.adopetbackendapi.domain.PetStatus

data class PetUpdate(
    val id: Long,
    val species: PetSpecies,
    var name: String,
    var size: PetSize,
    var personality: String,
    var city: String,
    var state: String,
    val owner: Long,
    var profilePictureUrl: String,
    var status: PetStatus,
)