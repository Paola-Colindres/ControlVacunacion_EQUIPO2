package com.example.ControlVacunacion.demo.model.Vacunas

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "vacunas")
data class Vacunas(val id_fabricante: Long = 0,
                   val nombre: String = "",
                   val numeroLote: Long = 0,
                   val fechaFabricacion : LocalDate? = null,
                   val fechaVencimiento : LocalDate? = null,
                   val fechaLlegada : LocalDate? = null){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_vacuna : Long = 0
}
