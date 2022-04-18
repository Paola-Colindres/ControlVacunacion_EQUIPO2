package com.example.ControlVacunacion.demo.business.CarnetEncabezadoBusiness

import com.example.ControlVacunacion.demo.model.CarnetEncabezado.CarnetEncabezado

interface ICarnetEncabezadoBusiness {
    fun getCarnetEncabezado():List<CarnetEncabezado>
    fun getCarnetsEncabezadoById(idCarnetEncabezado:Int): CarnetEncabezado
    fun saveCarnetEncabezado(carnetEncabezado: CarnetEncabezado): CarnetEncabezado
    fun saveCarnetsEncabezado(carnetsEncabezado: List<CarnetEncabezado>):List<CarnetEncabezado>
    fun removeCarnetEncabezado(idCarnetEncabezado: Int)
    fun updateCarnetEncabezado(carnetEncabezado: CarnetEncabezado): CarnetEncabezado
    fun getCarnetEncabezadoByNumeroCarnet(numeroCarnet: Long): CarnetEncabezado
}