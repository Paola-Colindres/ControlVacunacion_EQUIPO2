package com.example.ControlVacunacion.demo.model.CarnetEncabezado

import javax.persistence.*

@Entity
@Table(name="carnetEncabezado")
data class CarnetEncabezado(val id_civil:Long    =0,
                            val numeroCarnet:Long=0){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_carnetEncabezado:Int=0
}
