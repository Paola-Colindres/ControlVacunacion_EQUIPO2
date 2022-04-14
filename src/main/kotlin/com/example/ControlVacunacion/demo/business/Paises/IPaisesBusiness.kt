package com.example.ControlVacunacion.demo.business.Paises

import com.example.ControlVacunacion.demo.model.Paises.Paises


interface IPaisesBusiness {
    fun getPaises():List<Paises>
    fun getPaisesById(idPais:Long): Paises
    fun savePais(pais: Paises): Paises
    fun savePaises(paises: List<Paises>):List<Paises>
    fun removePais(idPais: Long)
    fun updatePais(pais: Paises): Paises
    fun getPaisByNombre(nombrePais: String): Paises
}