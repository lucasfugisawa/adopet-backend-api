package br.com.fugisawa.adopetbackendapi.controller

import br.com.fugisawa.adopetbackendapi.domain.pet.PetSize
import br.com.fugisawa.adopetbackendapi.domain.pet.PetSpecies
import br.com.fugisawa.adopetbackendapi.domain.pet.PetStatus
import br.com.fugisawa.adopetbackendapi.dto.*
import br.com.fugisawa.adopetbackendapi.service.PetService
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
@RequestMapping("/pet")
class PetController(private val petService: PetService) {

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_USER', 'STANDARD_USER')")
    fun list(
        @PageableDefault(
            size = 20,
            sort = ["name"],
            direction = Sort.Direction.ASC
        ) pageable: Pageable
    ) = petService.findAll(pageable)

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_USER', 'STANDARD_USER')")
    fun list(@PathVariable id: Long) =
        petService.findById(id)?.let { ResponseEntity.ok(it) }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Pet $id not found.")

    @GetMapping("/name/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN_USER', 'STANDARD_USER')")
    fun listByName(
        @PathVariable name: String,
        @PageableDefault(size = 20, sort = ["name"], direction = Sort.Direction.ASC) pageable: Pageable,
    ) = petService.findByName(name, pageable)

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN_USER', 'STANDARD_USER')")
    fun searchPets(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) species: PetSpecies?,
        @RequestParam(required = false) size: PetSize?,
        @RequestParam(required = false) city: String?,
        @RequestParam(required = false) state: String?,
        @RequestParam(required = false) status: PetStatus?,
        @RequestParam(required = false) ownerId: Long?,
        @PageableDefault(size = 20, sort = ["name"], direction = Sort.Direction.ASC) pageable: Pageable,
    ) = petService.searchPets(name, species, size, city, state, status, ownerId, pageable)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN_USER', 'STANDARD_USER')")
    fun create(@RequestBody pet: PetCreate) = petService.create(pet)

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN_USER', 'STANDARD_USER')")
    fun updatePet(@RequestBody pet: PetUpdate) = petService.update(pet)

    @PutMapping("/{id}/approve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    fun makeAvailable(@PathVariable id: Long) = petService.updateStatus(id, PetStatus.AVAILABLE)

    @PutMapping("/{id}/quarantine")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    fun makeQuarantined(@PathVariable id: Long) = petService.updateStatus(id, PetStatus.QUARANTINE)

    @PutMapping("/{id}/adopt")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN_USER', 'STANDARD_USER')")
    fun makeAdopted(@PathVariable id: Long) = petService.updateStatus(id, PetStatus.ADOPTED)

    @PutMapping("/{id}/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN_USER', 'STANDARD_USER')")
    fun makeRemoved(@PathVariable id: Long) = petService.updateStatus(id, PetStatus.REMOVED)

    @PutMapping("/{id}/suspend")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    fun makeSuspended(@PathVariable id: Long) = petService.updateStatus(id, PetStatus.SUSPENDED)

}