package com.example.ControlVacunacion.demo.dao.Municipios

import com.example.ControlVacunacion.demo.model.Municipios.Municipios
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MunicipiosRepository:JpaRepository<Municipios, Long> {
    fun findFirstByNombre(nombreMunicipio: String):Optional<Municipios>
}