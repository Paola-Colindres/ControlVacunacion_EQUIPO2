package com.example.ControlVacunacion.demo.dao.EstablecimientosDeSalud

import com.example.ControlVacunacion.demo.model.EstablecimientosDeSalud.Establecimientos
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EstablecimientosRepository:JpaRepository<Establecimientos, Long> {
    fun findFirstByNombre(nombreEstablecimiento: String): Optional<Establecimientos>
}