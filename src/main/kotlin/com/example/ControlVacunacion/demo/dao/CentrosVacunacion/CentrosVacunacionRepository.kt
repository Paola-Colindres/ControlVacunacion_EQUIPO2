package com.example.ControlVacunacion.demo.dao.CentrosVacunacion

import com.example.ControlVacunacion.demo.model.CentrosVacunacion.CentrosVacunacion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CentrosVacunacionRepository:JpaRepository<CentrosVacunacion, Long> {
    fun findFirstByNombre(nombreCentro:String): Optional<CentrosVacunacion>
}