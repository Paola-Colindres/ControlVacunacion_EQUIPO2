package com.example.ControlVacunacion.demo.model.Civiles

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "civiles")
data class Civiles(val dni:Long = 0,
                   val nombre:String = "",
                   val fechaNacimiento: LocalDate? = null,
                   val direccion:String = "",
                   val telefono:String = "",
                   val sexo: String = ""){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_civil : Long = 0
}
