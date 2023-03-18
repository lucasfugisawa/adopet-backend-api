package br.com.fugisawa.adopetbackendapi.repository

import br.com.fugisawa.adopetbackendapi.domain.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface UserRepository : JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    fun findByEmailIgnoreCase(name: String, pageable: Pageable): Page<User>
}