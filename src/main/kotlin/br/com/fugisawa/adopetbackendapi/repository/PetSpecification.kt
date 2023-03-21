package br.com.fugisawa.adopetbackendapi.repository

import br.com.fugisawa.adopetbackendapi.domain.pet.Pet
import br.com.fugisawa.adopetbackendapi.domain.pet.PetSize
import br.com.fugisawa.adopetbackendapi.domain.pet.PetSpecies
import br.com.fugisawa.adopetbackendapi.domain.pet.PetStatus
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

object PetSpecification {
    fun searchPets(
        name: String?,
        species: PetSpecies?,
        size: PetSize?,
        city: String?,
        state: String?,
        status: PetStatus?,
        ownerId: Long?
    ): Specification<Pet> {
        return Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()

            name?.let { predicates.add(criteriaBuilder.like(root.get<String>("name"), "%$it%")) }
            species?.let { predicates.add(criteriaBuilder.equal(root.get<PetSpecies>("species"), it)) }
            size?.let { predicates.add(criteriaBuilder.equal(root.get<PetSize>("size"), it)) }
            city?.let { predicates.add(criteriaBuilder.equal(root.get<String>("city"), it)) }
            state?.let { predicates.add(criteriaBuilder.equal(root.get<String>("state"), it)) }
            status?.let { predicates.add(criteriaBuilder.equal(root.get<PetStatus>("status"), it)) }
            ownerId?.let { predicates.add(criteriaBuilder.equal(root.join<Any, Any>("owner").get<Long>("id"), it)) }

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}