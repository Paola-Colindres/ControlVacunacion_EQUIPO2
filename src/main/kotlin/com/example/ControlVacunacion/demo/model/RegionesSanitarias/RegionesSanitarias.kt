package com.example.ControlVacunacion.demo.model.RegionesSanitarias

import javax.persistence.*

@Entity
@Table(name = "regionesSanitarias")
data class RegionesSanitarias(val departamento:String = "", val jefatura:String = "",
                              val telefono:Long = 0) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_region:Long = 0
}
