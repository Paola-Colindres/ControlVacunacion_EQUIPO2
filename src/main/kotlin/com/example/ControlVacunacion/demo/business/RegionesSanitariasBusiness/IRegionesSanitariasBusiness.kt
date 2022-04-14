package com.example.ControlVacunacion.demo.business.RegionesSanitariasBusiness

import com.example.ControlVacunacion.demo.model.RegionesSanitarias.RegionesSanitarias

interface IRegionesSanitariasBusiness {
    fun getRegionesSanitarias():List<RegionesSanitarias>
    fun getRegionesSanitariasById(idRegion:Long):RegionesSanitarias
    fun saveRegionSanitaria(region: RegionesSanitarias):RegionesSanitarias
    fun saveRegionesSanitarias(regiones: List<RegionesSanitarias>):List<RegionesSanitarias>
    fun removeRegionSanitaria(idRegion: Long)
    fun updateRegionSanitaria(region: RegionesSanitarias):RegionesSanitarias
    fun getRegionSanitariaByDepartamento(departamento: String):RegionesSanitarias
}