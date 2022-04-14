package com.example.ControlVacunacion.demo.model.EstablecimientosDeSalud

import javax.persistence.*

@Entity
@Table(name = "establecimientos")
data class Establecimientos(val nombre:String = "",
                            val direccion:String = "",
                            val telefono:Long = 0,
                            val id_municipio:Long = 0) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_establecimiento:Long = 0
}
