package br.com.fugisawa.adopetbackendapi.controller

import br.com.fugisawa.adopetbackendapi.dto.PetsPerSizeView
import br.com.fugisawa.adopetbackendapi.dto.PetsPerSpeciesView
import br.com.fugisawa.adopetbackendapi.service.PetReportService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/report/pet")
class PetReportController(private val petReportService: PetReportService) {

    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @RequestMapping("/perSize")
    fun petsPerSize(): List<PetsPerSizeView> = petReportService.petsPerSize()

    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @RequestMapping("/perSpecies")
    fun petsPerSpecies(): List<PetsPerSpeciesView> = petReportService.petsPerSpecies()

}