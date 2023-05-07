package br.com.fugisawa.adopetbackendapi.service

import br.com.fugisawa.adopetbackendapi.domain.user.User
import br.com.fugisawa.adopetbackendapi.dto.UserCreate
import br.com.fugisawa.adopetbackendapi.dto.UserUpdate
import br.com.fugisawa.adopetbackendapi.dto.UserView
import br.com.fugisawa.adopetbackendapi.repository.UserRepository
import br.com.fugisawa.adopetbackendapi.repository.UserSpecification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userToView: (User) -> (UserView),
    private val userCreateToUser: (UserCreate) -> (User),
) {

    fun findAll(pageable: Pageable): Page<UserView> {
        return userRepository.findAll(pageable).map(userToView)
    }

    fun findById(id: Long) = userRepository.findById(id).getOrNull()?.let(userToView)

    fun findByEmail(email: String, pageable: Pageable) =
        userRepository.findByEmailIgnoreCase(email, pageable).map(userToView)

    fun searchUsers(
        email: String? = null,
        name: String? = null,
        enabled: Boolean? = null,
        pageable: Pageable,
    ): Page<UserView> =
        userRepository.findAll(UserSpecification.searchUsers(email, name, enabled), pageable).map(userToView)

    @Transactional
    fun create(user: UserCreate): UserView? = userRepository.save(user.let(userCreateToUser)).let(userToView)

    @Transactional
    fun update(user: UserUpdate) {
        userRepository.findById(user.id).getOrNull()
            ?.apply {
                name = user.name
                email = user.email
                password = user.password
                about = user.about
                profilePictureUrl = user.profilePictureUrl
            }
            ?.also { userRepository.save(it) }
    }

    @Transactional
    fun disable(id: Long) = userRepository.getReferenceById(id)
        .apply { enabled = false }
        .run { userRepository.save(this) }

    @Transactional
    fun enable(id: Long) = userRepository.getReferenceById(id)
        .apply { enabled = true }
        .run { userRepository.save(this) }

}