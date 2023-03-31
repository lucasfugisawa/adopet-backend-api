package br.com.fugisawa.adopetbackendapi.service

import br.com.fugisawa.adopetbackendapi.domain.pet.Pet
import br.com.fugisawa.adopetbackendapi.domain.pet.PetSize
import br.com.fugisawa.adopetbackendapi.domain.pet.PetSpecies
import br.com.fugisawa.adopetbackendapi.domain.pet.PetStatus
import br.com.fugisawa.adopetbackendapi.dto.PetCreate
import br.com.fugisawa.adopetbackendapi.dto.PetUpdate
import br.com.fugisawa.adopetbackendapi.dto.PetView
import br.com.fugisawa.adopetbackendapi.repository.PetRepository
import br.com.fugisawa.adopetbackendapi.repository.PetSpecification
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class PetService(
    private val petRepository: PetRepository,
    private val petCreateToPet: (PetCreate) -> (Pet),
    private val petToView: (Pet) -> (PetView),
) {

    fun findAll(pageable: Pageable) = petRepository.findAll(pageable).map(petToView)

    fun findById(id: Long) = petRepository.findById(id).getOrNull()?.let(petToView)

    fun findByName(name: String, pageable: Pageable) =
        petRepository.findByNameContainingIgnoreCase(name, pageable).map(petToView)

    fun searchPets(
        name: String? = null,
        species: PetSpecies? = null,
        size: PetSize? = null,
        city: String? = null,
        state: String? = null,
        status: PetStatus? = null,
        ownerId: Long? = null,
        pageable: Pageable,
    ) = petRepository.findAll(PetSpecification.searchPets(name, species, size, city, state, status, ownerId), pageable)
        .map(petToView)

    fun create(pet: PetCreate) = petRepository.save(pet.let(petCreateToPet)).let(petToView)

    fun update(pet: PetUpdate) =
        petRepository.findById(pet.id).get()
            ?.apply {
                name = pet.name
                species = pet.species
                size = pet.size
                personality = pet.personality
                city = pet.city
                state = pet.state
                profilePictureUrl = pet.profilePictureUrl
                status = pet.status
            }
            ?.also { petRepository.save(it) }

    fun updateStatus(id: Long, status: PetStatus) = petRepository.findById(id).getOrNull()
        ?.apply { this.status = status }
        ?.also { petRepository.save(it) }
}
