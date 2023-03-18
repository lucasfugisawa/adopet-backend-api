package br.com.fugisawa.adopetbackendapi.dto.mapper

import br.com.fugisawa.adopetbackendapi.domain.Pet
import br.com.fugisawa.adopetbackendapi.dto.PetCreate
import br.com.fugisawa.adopetbackendapi.exception.NotFoundException
import br.com.fugisawa.adopetbackendapi.repository.UserRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class PetCreateMapper(private val userRepository: UserRepository): (PetCreate) -> (Pet) {
    override fun invoke(pet: PetCreate): Pet {
        return Pet(
            name = pet.name,
            size = pet.size,
            personality = pet.personality,
            city = pet.city,
            state = pet.state,
            owner = userRepository.findById(pet.owner).orElseThrow { NotFoundException("User ${pet.owner} not found") },
            profilePictureUrl = pet.profilePictureUrl,
            status = pet.status,
            statusDate = LocalDateTime.now(),
            creationDate = LocalDateTime.now(),
            species = pet.species,
        )
    }
}