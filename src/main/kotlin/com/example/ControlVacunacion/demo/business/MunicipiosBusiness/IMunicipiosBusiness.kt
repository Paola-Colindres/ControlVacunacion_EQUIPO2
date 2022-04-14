package com.example.ControlVacunacion.demo.business.MunicipiosBusiness

import com.example.ControlVacunacion.demo.model.Municipios.Municipios

interface IMunicipiosBusiness {
    fun getMunicipios():List<Municipios>
    fun getMunicipiosById(idMunicipio:Long): Municipios
    fun saveMunicipio(municipio: Municipios): Municipios
    fun saveMunicipios(municipios: List<Municipios>):List<Municipios>
    fun removeMunicipio(idMunicipio: Long)
    fun updateMunicipio(municipio: Municipios): Municipios
    fun getMunicipioByNombre(nombreMunicipio: String): Municipios
}