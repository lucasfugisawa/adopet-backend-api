package br.com.fugisawa.adopetbackendapi.repository

import br.com.fugisawa.adopetbackendapi.domain.pet.Pet
import br.com.fugisawa.adopetbackendapi.dto.PetsPerSizeView
import br.com.fugisawa.adopetbackendapi.dto.PetsPerSpeciesView
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface PetRepository : JpaRepository<Pet, Long>, JpaSpecificationExecutor<Pet> {
    fun findByNameContainingIgnoreCase(name: String, pageable: Pageable): Page<Pet>

    @Query("SELECT new br.com.fugisawa.adopetbackendapi.dto.PetsPerSizeView(p.size, count(p)) FROM Pet p WHERE p.status = 'AVAILABLE' GROUP BY p.size")
    fun petsPerSize(): List<PetsPerSizeView>

    @Query("SELECT new br.com.fugisawa.adopetbackendapi.dto.PetsPerSpeciesView(p.species, count(p)) FROM Pet p WHERE p.status = 'AVAILABLE' GROUP BY p.species")
    fun petsPerSpecies(): List<PetsPerSpeciesView>
}