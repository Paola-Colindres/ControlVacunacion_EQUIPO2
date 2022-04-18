package com.example.ControlVacunacion.demo.business.CargosBusiness

import com.example.ControlVacunacion.demo.model.Cargos.Cargos

interface ICargosBusiness {
    fun getCargos():List<Cargos>
    fun getCargoById(idCargos:Int): Cargos
    fun saveCargo(cargo: Cargos): Cargos
    fun saveCargos(cargos: List<Cargos>):List<Cargos>
    fun removeCargo(idCargo: Int)
    fun updateCargo(cargo: Cargos): Cargos
    fun getCargoByNombre(nombreCargo: String): Cargos
}