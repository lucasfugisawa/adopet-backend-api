package br.com.fugisawa.adopetbackendapi.controller

import br.com.fugisawa.adopetbackendapi.dto.PetsPerSizeView
import br.com.fugisawa.adopetbackendapi.dto.PetsPerSpeciesView
import br.com.fugisawa.adopetbackendapi.service.PetReportService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/report/pet")
class PetReportController(private val petReportService: PetReportService) {

    @RequestMapping("/perSize")
    fun petsPerSize(): List<PetsPerSizeView> = petReportService.petsPerSize()

    @RequestMapping("/perSpecies")
    fun petsPerSpecies(): List<PetsPerSpeciesView> = petReportService.petsPerSpecies()

}