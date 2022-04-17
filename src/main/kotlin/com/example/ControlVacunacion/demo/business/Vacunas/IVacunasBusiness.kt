package com.example.ControlVacunacion.demo.business.Vacunas

import com.example.ControlVacunacion.demo.model.Vacunas.Vacunas

interface IVacunasBusiness {
    fun getVacunas():List<Vacunas>
    fun getVacunaById(idVacuna:Long): Vacunas
    fun saveVacuna(vacuna: Vacunas): Vacunas
    fun saveVacunas(vacunas: List<Vacunas>):List<Vacunas>
    fun removeVacuna(idVacuna: Long)
    fun updateVacuna(vacuna: Vacunas): Vacunas
    fun getVacunaByNombre(nombreVacuna: String): Vacunas
}