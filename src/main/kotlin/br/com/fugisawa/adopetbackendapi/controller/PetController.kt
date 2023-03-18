package br.com.fugisawa.adopetbackendapi.controller

import br.com.fugisawa.adopetbackendapi.domain.PetSize
import br.com.fugisawa.adopetbackendapi.domain.PetSpecies
import br.com.fugisawa.adopetbackendapi.domain.PetStatus
import br.com.fugisawa.adopetbackendapi.dto.*
import br.com.fugisawa.adopetbackendapi.service.PetService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pet")
class PetController(private val petService: PetService) {

    @GetMapping
    fun list(@PageableDefault(size = 20, sort = ["name"], direction = Sort.Direction.ASC) pageable: Pageable): Page<PetView> {
        return petService.findAll(pageable)
    }

    @GetMapping("/{id}")
    fun list(@PathVariable id: Long): PetView? {
        return petService.findById(id)
    }

    @GetMapping("/name/{name}")
    fun listByName(
        @PathVariable name: String,
        @PageableDefault(size = 20, sort = ["name"], direction = Sort.Direction.ASC) pageable: Pageable,
    ): Page<PetView> {
        return petService.findByName(name, pageable)
    }

    @GetMapping("/search")
    fun searchPets(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) species: PetSpecies?,
        @RequestParam(required = false) size: PetSize?,
        @RequestParam(required = false) city: String?,
        @RequestParam(required = false) state: String?,
        @RequestParam(required = false) status: PetStatus?,
        @RequestParam(required = false) ownerId: Long?,
        @PageableDefault(size = 20, sort = ["name"], direction = Sort.Direction.ASC) pageable: Pageable,
    ): Page<PetView> {
        return petService.searchPets(name, species, size, city, state, status, ownerId, pageable)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    fun create(@RequestBody pet: PetCreate): PetView? {
        return petService.create(pet)
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    fun updateUser(@RequestBody pet: PetUpdate) {
        petService.update(pet)
    }

    @PutMapping("/{id}/approve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    fun makeAvailable(@PathVariable id: Long) {
        petService.updateStatus(id, PetStatus.AVAILABLE)
    }

    @PutMapping("/{id}/quarantine")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    fun makeQuarantined(@PathVariable id: Long) {
        petService.updateStatus(id, PetStatus.QUARANTINE)
    }

    @PutMapping("/{id}/adopt")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    fun makeAdopted(@PathVariable id: Long) {
        petService.updateStatus(id, PetStatus.ADOPTED)
    }

    @PutMapping("/{id}/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    fun makeRemoved(@PathVariable id: Long) {
        petService.updateStatus(id, PetStatus.REMOVED)
    }

    @PutMapping("/{id}/suspend")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    fun makeSuspended(@PathVariable id: Long) {
        petService.updateStatus(id, PetStatus.SUSPENDED)
    }
}