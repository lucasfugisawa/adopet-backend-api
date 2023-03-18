package br.com.fugisawa.adopetbackendapi.dto.mapper

import br.com.fugisawa.adopetbackendapi.domain.User
import br.com.fugisawa.adopetbackendapi.dto.UserCreate
import org.springframework.stereotype.Component

@Component
class UserCreateMapper(): (UserCreate) -> (User) {
    override fun invoke(t: UserCreate): User {
        return User(
            name = t.name,
            email = t.email,
            password = t.password,
            about = t.about,
            profilePictureUrl = t.profilePictureUrl,
        )
    }
}