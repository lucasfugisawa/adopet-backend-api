package br.com.fugisawa.adopetbackendapi.dto

import br.com.fugisawa.adopetbackendapi.domain.PetSize

data class PetsPerSizeView(
    val size: PetSize,
    val count: Long,
)