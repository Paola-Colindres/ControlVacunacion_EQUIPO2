package com.example.ControlVacunacion.demo.model.Paises

import javax.persistence.*

@Entity
@Table(name = "paises")
data class Paises(val codigo_iso:String = "",
                  val nombre:String = "",
                  val codigo_area:String = "") {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_pais: Long = 0
}
