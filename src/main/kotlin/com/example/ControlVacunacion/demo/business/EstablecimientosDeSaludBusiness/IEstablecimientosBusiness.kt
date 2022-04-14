package com.example.ControlVacunacion.demo.business.EstablecimientosDeSaludBusiness

import com.example.ControlVacunacion.demo.model.EstablecimientosDeSalud.Establecimientos


interface IEstablecimientosBusiness {
    fun getEstablecimientos():List<Establecimientos>
    fun getEstablecimientosById(idEstablecimiento:Long): Establecimientos
    fun saveEstablecimiento(establecimiento: Establecimientos): Establecimientos
    fun saveEstablecimientos(establecimientos: List<Establecimientos>):List<Establecimientos>
    fun removeEstablecimiento(idEstablecimiento: Long)
    fun updateEstablecimiento(establecimiento: Establecimientos): Establecimientos
    fun getEstablecimientoByNombre(nombreEstablecimiento: String): Establecimientos
}