package com.example.ControlVacunacion.demo.dao.Cargos

import com.example.ControlVacunacion.demo.model.Cargos.Cargos
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CargosRepository:JpaRepository<Cargos, Int> {
    fun findFirstByNombre(nombre:String):Optional<Cargos>
}