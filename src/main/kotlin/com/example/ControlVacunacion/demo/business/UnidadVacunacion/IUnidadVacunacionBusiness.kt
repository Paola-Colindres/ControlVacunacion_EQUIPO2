package com.example.ControlVacunacion.demo.business.UnidadVacunacion

import com.example.ControlVacunacion.demo.model.UnidadVacunacion.UnidadVacunacion

interface IUnidadVacunacionBusiness {
    fun getUnidades():List<UnidadVacunacion>
    fun getUnidadById(idUnidad:Long): UnidadVacunacion
    fun saveUnidad(Unidad: UnidadVacunacion): UnidadVacunacion
    fun saveUnidades(Unidades: List<UnidadVacunacion>):List<UnidadVacunacion>
    fun removeUnidad(idUnidad: Long)
    fun updateUnidad(Unidad: UnidadVacunacion): UnidadVacunacion
    fun getUnidadByTipo(tipoUnidad: String): UnidadVacunacion
}