package com.example.ControlVacunacion.demo.model.Comorbilidad

import javax.persistence.*

@Entity
@Table(name = "comorbilidades")
data class Comorbilidad(val nombre:String = "",
                        val tipo: String = ""){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_comorbilidad : Long = 0
}
