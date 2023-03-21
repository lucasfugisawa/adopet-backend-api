package br.com.fugisawa.adopetbackendapi.service

import br.com.fugisawa.adopetbackendapi.domain.user.User
import br.com.fugisawa.adopetbackendapi.dto.UserCreate
import br.com.fugisawa.adopetbackendapi.dto.UserUpdate
import br.com.fugisawa.adopetbackendapi.dto.UserView
import br.com.fugisawa.adopetbackendapi.exception.NotFoundException
import br.com.fugisawa.adopetbackendapi.repository.UserRepository
import br.com.fugisawa.adopetbackendapi.repository.UserSpecification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userToView: (User) -> (UserView),
    private val userCreateToUser: (UserCreate) -> (User),
) {

    fun findAll(pageable: Pageable): Page<UserView> {
        return userRepository.findAll(pageable).map(userToView)
    }

    fun findById(id: Long): UserView? {
        val savedUser = userRepository.findById(id).orElseThrow { NotFoundException("User $id not found.") }
        return savedUser.let(userToView)
    }

    fun findByEmail(email: String, pageable: Pageable): Page<UserView> {
        val users = userRepository.findByEmailIgnoreCase(email, pageable)
        return users.map(userToView)
    }

    fun searchUsers(
        email: String? = null,
        name: String? = null,
        enabled: Boolean? = null,
        pageable: Pageable,
    ): Page<UserView> {
        val users = userRepository.findAll(UserSpecification.searchUsers(email, name, enabled), pageable)
        return users.map(userToView)
    }

    fun create(user: UserCreate): UserView? {
        val savedUser = userRepository.save(user.let(userCreateToUser))
        return savedUser.let(userToView)
    }

    fun update(user: UserUpdate) {
        val savedUser = userRepository.findById(user.id).orElseThrow { NotFoundException("User ${user.id} not found.") }
        with(savedUser) {
            email = user.email
            name = user.name
            password = user.password
            about = user.about
            profilePictureUrl = user.profilePictureUrl
        }
        userRepository.save(savedUser)
    }

    fun disable(id: Long) {
        val savedUser = userRepository.getReferenceById(id).apply { enabled = false }
        userRepository.save(savedUser)
    }

    fun enable(id: Long) {
        val savedUser = userRepository.getReferenceById(id).apply { enabled = true }
        savedUser.enabled = true
        userRepository.save(savedUser)
    }

}