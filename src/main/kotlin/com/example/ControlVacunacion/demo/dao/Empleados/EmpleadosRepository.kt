package com.example.ControlVacunacion.demo.dao.Empleados

import com.example.ControlVacunacion.demo.model.CentrosVacunacion.CentrosVacunacion
import com.example.ControlVacunacion.demo.model.Empleados.Empleados
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EmpleadosRepository:JpaRepository<Empleados, Int> {
    fun findFirstByNombre(nombre:String):Optional<Empleados>
}