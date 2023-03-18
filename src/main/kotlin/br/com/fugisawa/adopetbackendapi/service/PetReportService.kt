package br.com.fugisawa.adopetbackendapi.service

import br.com.fugisawa.adopetbackendapi.dto.PetsPerSizeView
import br.com.fugisawa.adopetbackendapi.dto.PetsPerSpeciesView
import br.com.fugisawa.adopetbackendapi.repository.PetRepository
import org.springframework.stereotype.Service

@Service
class PetReportService(private val petRepository: PetRepository) {

    fun petsPerSize(): List<PetsPerSizeView> = petRepository.petsPerSize()
    fun petsPerSpecies(): List<PetsPerSpeciesView> = petRepository.petsPerSpecies()
}