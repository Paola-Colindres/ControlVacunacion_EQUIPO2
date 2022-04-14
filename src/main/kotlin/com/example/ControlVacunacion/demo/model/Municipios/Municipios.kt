package com.example.ControlVacunacion.demo.model.Municipios

import javax.persistence.*

@Entity
@Table(name = "municipios")
data class Municipios(val nombre:String = "",
                      val id_region:Long = 0) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_municipio:Long = 0
}
