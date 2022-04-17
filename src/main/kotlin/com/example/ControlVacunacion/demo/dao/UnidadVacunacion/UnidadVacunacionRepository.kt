package com.example.ControlVacunacion.demo.dao.UnidadVacunacion

import com.example.ControlVacunacion.demo.model.UnidadVacunacion.UnidadVacunacion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UnidadVacunacionRepository : JpaRepository<UnidadVacunacion, Long> {
    fun findFirstByTipo(tipo:String) : Optional<UnidadVacunacion>
}