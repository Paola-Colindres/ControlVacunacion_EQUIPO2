package com.example.ControlVacunacion.demo.dao.CivilComorbilidad

import com.example.ControlVacunacion.demo.model.Civil_Comorbilidad.Civil_Comorbilidad
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CivilComorbilidadRepository: JpaRepository<Civil_Comorbilidad , Long> {
    fun findFirstByEstado(estado:String) : Optional<Civil_Comorbilidad>
}