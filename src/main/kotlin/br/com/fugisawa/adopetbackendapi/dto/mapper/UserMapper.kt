package br.com.fugisawa.adopetbackendapi.dto.mapper

import br.com.fugisawa.adopetbackendapi.domain.user.User
import br.com.fugisawa.adopetbackendapi.dto.UserView
import org.springframework.stereotype.Component

@Component
class UserMapper(): (User) -> (UserView) {
    override fun invoke(t: User): UserView {
        return UserView(
            id = t.id,
            name = t.name,
            email = t.email,
            about = t.about,
            profilePictureUrl = t.profilePictureUrl,
            enabled = t.enabled,
            creationDate = t.creationDate,
        )
    }
}