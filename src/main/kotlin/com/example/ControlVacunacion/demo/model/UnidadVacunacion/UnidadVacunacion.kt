package com.example.ControlVacunacion.demo.model.UnidadVacunacion

import javax.persistence.*

@Entity
@Table(name = "unidadesVacunacion")
data class UnidadVacunacion(val id_centro: Long = 0,
                            val id_vacuna_suministrar: Long = 0,
                            val tipo:String = ""){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_unidad : Long = 0
}
