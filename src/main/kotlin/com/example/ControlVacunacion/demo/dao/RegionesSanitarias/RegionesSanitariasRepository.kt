package com.example.ControlVacunacion.demo.dao.RegionesSanitarias

import com.example.ControlVacunacion.demo.model.RegionesSanitarias.RegionesSanitarias
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RegionesSanitariasRepository:JpaRepository<RegionesSanitarias, Long> {
    fun findFirstByDepartamento(departamento:String): Optional<RegionesSanitarias>
}