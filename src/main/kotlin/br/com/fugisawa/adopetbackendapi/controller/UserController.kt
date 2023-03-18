package br.com.fugisawa.adopetbackendapi.controller

import br.com.fugisawa.adopetbackendapi.dto.UserCreate
import br.com.fugisawa.adopetbackendapi.dto.UserUpdate
import br.com.fugisawa.adopetbackendapi.dto.UserView
import br.com.fugisawa.adopetbackendapi.service.UserService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(val userService: UserService) {

    @GetMapping
    @Cacheable("users")
    fun listUsers(
        @PageableDefault(size = 20, sort = ["name"], direction = Sort.Direction.ASC) pageable: Pageable
    ): Page<UserView> {
        return userService.findAll(pageable)
    }

    @GetMapping("/{id}")
    @Cacheable("users")
    fun listUser(@PathVariable id: Long): UserView? {
        return userService.findById(id)
    }

    @GetMapping("/email/{email}")
    @Cacheable("users")
    fun listUsersByEmail(
        @RequestParam email: String,
        @PageableDefault(size = 20, sort = ["name"], direction = Sort.Direction.ASC) pageable: Pageable,
    ): Page<UserView> {
        return userService.findByEmail(email, pageable)
    }

    @GetMapping("/search")
    @Cacheable("users")
    fun searchUsers(
        @RequestParam(required = false) email: String?,
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) enabled: Boolean?,
        @PageableDefault(size = 20, sort = ["name"], direction = Sort.Direction.ASC) pageable: Pageable,
    ): Page<UserView> {
        return userService.searchUsers(email, name, enabled, pageable)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    @CacheEvict("users", allEntries = true)
    fun createUser(@RequestBody user: UserCreate): UserView? {
        return userService.create(user)
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict("users", allEntries = true)
    fun updateUser(@RequestBody user: UserUpdate) {
        userService.update(user)
    }

    @PostMapping("/disable/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict("users", allEntries = true)
    fun disableUser(@PathVariable id: Long) {
        userService.disable(id)
    }

    @PostMapping("/enable/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict("users", allEntries = true)
    fun enableUser(@PathVariable id: Long) {
        userService.enable(id)
    }

}