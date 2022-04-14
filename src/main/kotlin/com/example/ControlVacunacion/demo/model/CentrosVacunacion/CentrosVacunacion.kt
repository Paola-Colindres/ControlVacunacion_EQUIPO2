package com.example.ControlVacunacion.demo.model.CentrosVacunacion

import javax.persistence.*

@Entity
@Table(name = "centrosVacunacion")
data class CentrosVacunacion(val nombre:String = "",
                             val direccion:String = "",
                             val tipo:String = "",
                             val horario:String = "",
                             val id_establecimiento:Long = 0) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_centroVacunacion:Long = 0
}
