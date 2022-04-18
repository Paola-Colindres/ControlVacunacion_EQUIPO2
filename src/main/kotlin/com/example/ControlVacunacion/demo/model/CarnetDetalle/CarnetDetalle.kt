package com.example.ControlVacunacion.demo.model.CarnetDetalle

import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name="carnetDetalle")
data class CarnetDetalle(val id_carnetEncabezado:Int  =0,
                         val id_unidad:Int            =0,
                         val fecha: LocalDate?        = null,
                         val dosis:String             ="",
                         val observacion:String       ="",
                         val idEmpleadoVacuno:Int     =0){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_carnetDetalle:Int = 0
}
