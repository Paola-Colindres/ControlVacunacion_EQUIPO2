package com.example.ControlVacunacion.demo.dao.CarnetDetalle

import com.example.ControlVacunacion.demo.model.CarnetDetalle.CarnetDetalle
import com.example.ControlVacunacion.demo.model.CentrosVacunacion.CentrosVacunacion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CarnetDetalleRepository:JpaRepository<CarnetDetalle, Int> {
    fun findFirstByObservacion(observacion:String):Optional<CarnetDetalle>
}