package com.example.ControlVacunacion.demo.business.CarnetDetalleBusiness

import com.example.ControlVacunacion.demo.model.CarnetDetalle.CarnetDetalle

interface ICarnetDetalleBusiness {
    fun getCarnetDetalle():List<CarnetDetalle>
    fun getCarnetsDetalleById(idCarnetDetalle:Int): CarnetDetalle
    fun saveCarnetDetalle(carnetDetalle: CarnetDetalle): CarnetDetalle
    fun saveCarnetsDetalle(carnetsDetalle: List<CarnetDetalle>):List<CarnetDetalle>
    fun removeCarnetDetalle(idCarnetDetalle: Int)
    fun updateCarnetDetalle(carnetDetalle: CarnetDetalle): CarnetDetalle
    fun getCarnetDetalleByObservacion(observacion: String): CarnetDetalle
}