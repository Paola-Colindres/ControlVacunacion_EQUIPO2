package com.example.ControlVacunacion.demo.dao.Fabricantes

import com.example.ControlVacunacion.demo.model.Fabricantes.fabricantes
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FabricantesRepository : JpaRepository<fabricantes ,Long > {
    fun findFirstByLaboratorio(nombreLab: String) : Optional<fabricantes>
}