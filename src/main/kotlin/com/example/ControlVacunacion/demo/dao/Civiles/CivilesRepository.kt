package com.example.ControlVacunacion.demo.dao.Civiles

import com.example.ControlVacunacion.demo.model.Civiles.Civiles
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CivilesRepository:JpaRepository<Civiles, Long> {
    fun findFirstByNombre(nombre:String) : Optional<Civiles>
}