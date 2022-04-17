package com.example.ControlVacunacion.demo.business.CivilComorbilidad

import com.example.ControlVacunacion.demo.model.Civil_Comorbilidad.Civil_Comorbilidad

interface ICivilComorbilidadBusiness {
    fun getCivilesComorbilidades():List<Civil_Comorbilidad>
    fun getCivilComorbilidadById(idCivilComorb:Long): Civil_Comorbilidad
    fun saveCivilComorbilidad(civilComorb: Civil_Comorbilidad): Civil_Comorbilidad
    fun saveCivilesComorbilidades(civilesComorbilidades: List<Civil_Comorbilidad>):List<Civil_Comorbilidad>
    fun removeCivilComorbilidad(idCivilComorb: Long)
    fun updateCivilComorbilidad(civilComorb: Civil_Comorbilidad): Civil_Comorbilidad
    fun getCivilComorbilidadByEstado(estado: String): Civil_Comorbilidad
}
