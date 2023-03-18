package br.com.fugisawa.adopetbackendapi.service

import br.com.fugisawa.adopetbackendapi.domain.Pet
import br.com.fugisawa.adopetbackendapi.domain.PetSize
import br.com.fugisawa.adopetbackendapi.domain.PetSpecies
import br.com.fugisawa.adopetbackendapi.domain.PetStatus
import br.com.fugisawa.adopetbackendapi.dto.PetCreate
import br.com.fugisawa.adopetbackendapi.dto.PetUpdate
import br.com.fugisawa.adopetbackendapi.dto.PetView
import br.com.fugisawa.adopetbackendapi.exception.NotFoundException
import br.com.fugisawa.adopetbackendapi.repository.PetRepository
import br.com.fugisawa.adopetbackendapi.repository.PetSpecification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PetService(
    private val petRepository: PetRepository,
    private val petCreateToPet: (PetCreate) -> (Pet),
    private val petToView: (Pet) -> (PetView),
) {

    fun findAll(pageable: Pageable): Page<PetView> {
        return petRepository.findAll(pageable).map(petToView)
    }

    fun findById(id: Long): PetView? {
        val savedPet = petRepository.findById(id).orElseThrow { NotFoundException("Pet $id not found.") }
        return savedPet.let(petToView)
    }

    fun findByName(name: String, pageable: Pageable): Page<PetView> {
        return petRepository.findByNameContainingIgnoreCase(name, pageable).map(petToView)
    }

    fun searchPets(
        name: String? = null,
        species: PetSpecies? = null,
        size: PetSize? = null,
        city: String? = null,
        state: String? = null,
        status: PetStatus? = null,
        ownerId: Long? = null,
        pageable: Pageable,
    ): Page<PetView> {
        val pets =
            petRepository.findAll(PetSpecification.searchPets(name, species, size, city, state, status, ownerId), pageable)
        return pets.map(petToView)
    }

    fun create(pet: PetCreate): PetView? {
        val savedPet = petRepository.save(pet.let(petCreateToPet))
        return savedPet.let(petToView)
    }

    fun update(pet: PetUpdate) {
        val savedPet = petRepository.findById(pet.id).orElseThrow { NotFoundException("Pet ${pet.id} not found.") }
        with(savedPet) {
            name = pet.name
            species = pet.species
            size = pet.size
            personality = pet.personality
            city = pet.city
            state = pet.state
            profilePictureUrl = pet.profilePictureUrl
            status = pet.status
        }
        petRepository.save(savedPet)
    }

    fun updateStatus(id: Long, status: PetStatus) {
        val savedPet = petRepository.findById(id).orElseThrow { NotFoundException("Pet $id not found.") }
        savedPet.status = status
        petRepository.save(savedPet)
    }
}
