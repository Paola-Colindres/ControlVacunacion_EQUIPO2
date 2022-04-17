package com.example.ControlVacunacion.demo.dao.Vacunas

import com.example.ControlVacunacion.demo.model.Vacunas.Vacunas
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface VacunasRepository: JpaRepository<Vacunas, Long> {
    fun findFirstByNombre(nombreVacuna: String) : Optional<Vacunas>
}