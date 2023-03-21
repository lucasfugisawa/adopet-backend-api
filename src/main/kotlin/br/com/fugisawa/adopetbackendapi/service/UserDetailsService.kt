package br.com.fugisawa.adopetbackendapi.service

import br.com.fugisawa.adopetbackendapi.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class UserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(s: String): UserDetails {
        val user = userRepository.findByEmailIgnoreCase(s)
        val authorities = ArrayList<GrantedAuthority>()
        if (user?.roles != null) {
            user.roles!!.forEach { authorities.add(SimpleGrantedAuthority(it.name)) }
        }
        return User(
            user?.email,
            user?.password,
            authorities
        )
    }
}
