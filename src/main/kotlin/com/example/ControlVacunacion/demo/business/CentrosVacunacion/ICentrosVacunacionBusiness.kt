package com.example.ControlVacunacion.demo.business.CentrosVacunacion

import com.example.ControlVacunacion.demo.model.CentrosVacunacion.CentrosVacunacion


interface ICentrosVacunacionBusiness {
    fun getCentrosVacunacion():List<CentrosVacunacion>
    fun getCentrosVacunacionById(idCentroVacunacion:Long): CentrosVacunacion
    fun saveCentroVacunacion(centroVacunacion: CentrosVacunacion): CentrosVacunacion
    fun saveCentrosVacunacion(centrosVacunacion: List<CentrosVacunacion>):List<CentrosVacunacion>
    fun removeCentroVacunacion(idCentroVacunacion: Long)
    fun updateCentroVacunacion(centroVacunacion: CentrosVacunacion): CentrosVacunacion
    fun getCentroVacunacionByNombre(nombreCentroVacunacion: String): CentrosVacunacion
}