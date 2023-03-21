package br.com.fugisawa.adopetbackendapi.repository

import br.com.fugisawa.adopetbackendapi.domain.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface UserRepository : JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    fun findByEmailIgnoreCase(email: String, pageable: Pageable): Page<User>
    fun findByEmailIgnoreCase(email: String): User?
}