package br.com.fugisawa.adopetbackendapi.dto.mapper

import br.com.fugisawa.adopetbackendapi.domain.user.User
import br.com.fugisawa.adopetbackendapi.dto.UserCreate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserCreateMapper(private val bCryptPasswordEncoder: BCryptPasswordEncoder): (UserCreate) -> (User) {
    override fun invoke(t: UserCreate): User {
        return User(
            name = t.name,
            email = t.email,
            password = bCryptPasswordEncoder.encode(t.password),
            about = t.about,
            profilePictureUrl = t.profilePictureUrl,
        )
    }
}