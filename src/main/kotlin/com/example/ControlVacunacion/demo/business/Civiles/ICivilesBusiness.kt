package com.example.ControlVacunacion.demo.business.Civiles

import com.example.ControlVacunacion.demo.model.Civiles.Civiles

interface ICivilesBusiness {
    fun getCiviles():List<Civiles>
    fun getCivilById(idCivil:Long): Civiles
    fun saveCivil(civil: Civiles): Civiles
    fun saveCiviles(civiles: List<Civiles>):List<Civiles>
    fun removeCivil(idCivil: Long)
    fun updateCivil(civil: Civiles): Civiles
    fun getCivilByNombre(nombre: String): Civiles
}