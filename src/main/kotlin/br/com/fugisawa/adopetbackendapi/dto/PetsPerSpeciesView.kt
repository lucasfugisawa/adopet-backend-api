package br.com.fugisawa.adopetbackendapi.dto

import br.com.fugisawa.adopetbackendapi.domain.pet.PetSpecies

data class PetsPerSpeciesView(
    val size: PetSpecies,
    val count: Long,
)