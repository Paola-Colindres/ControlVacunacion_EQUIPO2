package com.example.ControlVacunacion.demo.dao.Paises

import com.example.ControlVacunacion.demo.model.Paises.Paises
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PaisesRepository:JpaRepository<Paises, Long> {
    fun findFirstByNombre(nombrePais:String):Optional<Paises>
}