package com.example.ControlVacunacion.demo.model.Empleados

import javax.persistence.*

@Entity
@Table(name = "empleados")
data class Empleados(val codigo:String  ="",
                     val dni:String     ="",
                     val nombre:String  ="",
                     val telefono:Long  =0,
                     val correo:String  ="",
                     val password:String="",
                     val id_cargo:Int   =0,
                     val id_unidad:Int  =0){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_empleado:Int = 0
}
