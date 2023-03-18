package br.com.fugisawa.adopetbackendapi.repository

import br.com.fugisawa.adopetbackendapi.domain.User
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

object UserSpecification {

    fun searchUsers(
        email: String?,
        name: String?,
        enabled: Boolean?
    ): Specification<User> {
        return Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()

            email?.let { predicates.add(criteriaBuilder.equal(root.get<String>("email"), it)) }
            name?.let { predicates.add(criteriaBuilder.like(root.get<String>("name"), "%$it%")) }
            enabled?.let { predicates.add(criteriaBuilder.equal(root.get<Boolean>("enabled"), it)) }

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}
