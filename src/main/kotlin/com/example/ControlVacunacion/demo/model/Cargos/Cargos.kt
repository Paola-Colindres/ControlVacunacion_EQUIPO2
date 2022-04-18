package com.example.ControlVacunacion.demo.model.Cargos

import javax.persistence.*

@Entity
@Table(name="cargos")
data class Cargos(val nombre:String     ="",
                  val descripcion:String=""){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_cargo:Int=0
}
