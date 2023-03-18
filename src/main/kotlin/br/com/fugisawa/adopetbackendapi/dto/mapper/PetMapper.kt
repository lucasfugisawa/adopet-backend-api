package br.com.fugisawa.adopetbackendapi.dto.mapper

import br.com.fugisawa.adopetbackendapi.domain.Pet
import br.com.fugisawa.adopetbackendapi.domain.User
import br.com.fugisawa.adopetbackendapi.dto.PetView
import br.com.fugisawa.adopetbackendapi.dto.UserView
import org.springframework.stereotype.Component

@Component
class PetMapper(private val userToView: (User) -> (UserView)): (Pet) -> (PetView) {
    override fun invoke(t: Pet): PetView {
        return PetView(
            id = t.id,
            name = t.name,
            size = t.size,
            personality = t.personality,
            city = t.city,
            state = t.state,
            owner = t.owner.let(userToView),
            profilePictureUrl = t.profilePictureUrl,
            status = t.status,
            creationDate = t.creationDate,
            statusDate = t.statusDate,
            species = t.species,
        )
    }
}