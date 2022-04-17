package com.example.ControlVacunacion.demo.dao.Comorbilidad

import com.example.ControlVacunacion.demo.model.Comorbilidad.Comorbilidad
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ComorbilidadRepository:JpaRepository<Comorbilidad, Long> {
    fun findFirstByNombre(nombre:String) : Optional<Comorbilidad>
}