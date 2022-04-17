package com.example.ControlVacunacion.demo.model.Civil_Comorbilidad

import javax.persistence.*

@Entity
@Table(name = "civilComorbilidad")
data class Civil_Comorbilidad(val id_civil:Long = 0,
                              val id_comorbilidad: Long = 0,
                              val estado : String = "",
                              val observacion: String = ""){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_civilComorbilidad : Long = 0
}
