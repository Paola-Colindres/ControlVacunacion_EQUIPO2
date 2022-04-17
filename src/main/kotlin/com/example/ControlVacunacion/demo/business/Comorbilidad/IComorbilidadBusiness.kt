package com.example.ControlVacunacion.demo.business.Comorbilidad

import com.example.ControlVacunacion.demo.model.Comorbilidad.Comorbilidad

interface IComorbilidadBusiness {
    fun getComorbilidades():List<Comorbilidad>
    fun getComorbilidadById(idComorb:Long): Comorbilidad
    fun saveComorbilidad(comorb: Comorbilidad): Comorbilidad
    fun saveComorbilidades(comorbilidades: List<Comorbilidad>):List<Comorbilidad>
    fun removeComorbilidad(idComorb: Long)
    fun updateComorbilidad(Comorb: Comorbilidad): Comorbilidad
    fun getComorbilidadByNombre(nombreComorb: String): Comorbilidad
}
