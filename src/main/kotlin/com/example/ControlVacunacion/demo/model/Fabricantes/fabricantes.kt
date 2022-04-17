package com.example.ControlVacunacion.demo.model.Fabricantes

import javax.persistence.*

@Entity
@Table(name = "fabricantes")
data class fabricantes(val laboratorio: String = "",
                       val nombre_contacto: String = "",
                       val telefono_contacto: Long = 0L,
                       val id_pais: Long = 0) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id_fabricante : Long = 0
}

