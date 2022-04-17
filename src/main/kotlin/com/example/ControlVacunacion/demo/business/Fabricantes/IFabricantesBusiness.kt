package com.example.ControlVacunacion.demo.business.Fabricantes

import com.example.ControlVacunacion.demo.model.Fabricantes.fabricantes

interface IFabricantesBusiness {
    fun getFabricantes():List<fabricantes>
    fun getFabricanteById(idFabricante:Long): fabricantes
    fun saveFabricante(fabricante: fabricantes): fabricantes
    fun saveFabricantes(fabricantes: List<fabricantes>):List<fabricantes>
    fun removeFabricante(idFabricante: Long)
    fun updateFabricante(fabricante: fabricantes): fabricantes
    fun getFabrianteByLaboratorio(nombreLab: String): fabricantes
}