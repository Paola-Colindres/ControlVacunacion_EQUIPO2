package com.example.ControlVacunacion.demo.dao.CarnetEncabezado

import com.example.ControlVacunacion.demo.model.CarnetEncabezado.CarnetEncabezado
import com.example.ControlVacunacion.demo.model.CentrosVacunacion.CentrosVacunacion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CarnetEncabezadoRepository:JpaRepository<CarnetEncabezado, Int> {
    fun findFirstByNumeroCarnet(numeroCarnet:Long):Optional<CarnetEncabezado>
}