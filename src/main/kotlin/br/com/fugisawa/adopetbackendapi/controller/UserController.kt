package br.com.fugisawa.adopetbackendapi.controller

import br.com.fugisawa.adopetbackendapi.dto.UserCreate
import br.com.fugisawa.adopetbackendapi.dto.UserUpdate
import br.com.fugisawa.adopetbackendapi.service.UserService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @GetMapping
    @Cacheable("users")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    fun listUsers(
        @PageableDefault(size = 20, sort = ["name"], direction = Sort.Direction.ASC) pageable: Pageable
    ) = userService.findAll(pageable)

    @GetMapping("/{id}")
    @Cacheable("users")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    fun listUser(@PathVariable id: Long) =
        userService.findById(id)?.let { ResponseEntity.ok(it) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User $id not found.")

    @GetMapping("/email/{email}")
    @Cacheable("users")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    fun listUsersByEmail(
        @PathVariable email: String,
        @PageableDefault(size = 20, sort = ["name"], direction = Sort.Direction.ASC) pageable: Pageable,
    ) = userService.findByEmail(email, pageable)

    @GetMapping("/search")
    @Cacheable("users")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    fun searchUsers(
        @RequestParam(required = false) email: String?,
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) enabled: Boolean?,
        @PageableDefault(size = 20, sort = ["name"], direction = Sort.Direction.ASC) pageable: Pageable,
    ) = userService.searchUsers(email, name, enabled, pageable)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    @CacheEvict("users", allEntries = true)
    fun createUser(@RequestBody user: UserCreate) = userService.create(user)

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict("users", allEntries = true)
    @PreAuthorize("hasAnyAuthority('ADMIN_USER', 'STANDARD_USER')")
    fun updateUser(@RequestBody user: UserUpdate) = userService.update(user)

    @PostMapping("/disable/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict("users", allEntries = true)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    fun disableUser(@PathVariable id: Long) = userService.disable(id)

    @PostMapping("/enable/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict("users", allEntries = true)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    fun enableUser(@PathVariable id: Long) = userService.enable(id)

}