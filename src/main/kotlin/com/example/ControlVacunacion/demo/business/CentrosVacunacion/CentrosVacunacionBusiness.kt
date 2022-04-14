package com.example.ControlVacunacion.demo.business.CentrosVacunacion

import com.example.ControlVacunacion.demo.dao.CentrosVacunacion.CentrosVacunacionRepository
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.CentrosVacunacion.CentrosVacunacion
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.Throws

@Service
class CentrosVacunacionBusiness:ICentrosVacunacionBusiness {

    @Autowired
    val centroRepository: CentrosVacunacionRepository? = null

    @Throws(BusinessException::class)
    override fun getCentrosVacunacion(): List<CentrosVacunacion> {
        try {
            return centroRepository!!.findAll()
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getCentrosVacunacionById(idCentroVacunacion: Long): CentrosVacunacion {
        val opt:Optional<CentrosVacunacion>
        try {
            opt = centroRepository!!.findById(idCentroVacunacion)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontro el Centro de Vacunacion $idCentroVacunacion")
        return opt.get()
    }

    @Throws(BusinessException::class)
    override fun saveCentroVacunacion(centroVacunacion: CentrosVacunacion): CentrosVacunacion {
        try {
            validarCentrosVacunacion(centroVacunacion)
            return centroRepository!!.save(centroVacunacion)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class)
    override fun saveCentrosVacunacion(centrosVacunacion: List<CentrosVacunacion>): List<CentrosVacunacion> {
        try {
            for (centroVacunacion in centrosVacunacion) {
                validarCentrosVacunacion(centroVacunacion)
            }
            return centroRepository!!.saveAll(centrosVacunacion)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeCentroVacunacion(idCentroVacunacion: Long) {
        val opt:Optional<CentrosVacunacion>
        try {
            opt = centroRepository!!.findById(idCentroVacunacion)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontro el Centro de Vacunacion $idCentroVacunacion")
        else {
            try {
                centroRepository!!.deleteById(idCentroVacunacion)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun updateCentroVacunacion(centroVacunacion: CentrosVacunacion): CentrosVacunacion {
        val opt:Optional<CentrosVacunacion>
        try {
            opt = centroRepository!!.findById(centroVacunacion.id_centroVacunacion)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontro el Centro de Vacunacion ${centroVacunacion.id_centroVacunacion}")
        else {
            try {
                validarCentrosVacunacion(centroVacunacion)
                val centroExistente = CentrosVacunacion(
                        centroVacunacion.nombre,
                        centroVacunacion.direccion,
                        centroVacunacion.tipo,
                        centroVacunacion.horario,
                        centroVacunacion.id_establecimiento)
                centroExistente.id_centroVacunacion = centroVacunacion.id_centroVacunacion
                centroRepository!!.save(centroExistente)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
        return opt.get()
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getCentroVacunacionByNombre(nombreCentroVacunacion: String): CentrosVacunacion {
        val opt:Optional<CentrosVacunacion>
        try {
            opt = centroRepository!!.findFirstByNombre(nombreCentroVacunacion)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontro el Centro de Vacunacion: $nombreCentroVacunacion")
        return opt.get()
    }

    private fun validarCentrosVacunacion(centroVacunacion: CentrosVacunacion) {
        if (centroVacunacion.nombre.isEmpty()) {
            throw BusinessException("El nombre del Centro de Vacunacion no debe estar vacio")
        }
        if (centroVacunacion.nombre.length < 5) {
            throw BusinessException("Ingrese mas de cinco caracteres en el nombre del Centro de Vacunacion")
        }
        if (centroVacunacion.nombre.length > 50) {
            throw BusinessException("El nombre del Centro de Vacunacion es demasiado largo")
        }
        if (centroVacunacion.direccion.isEmpty()) {
            throw BusinessException("La direccion no debe estar vacia")
        }
        if (centroVacunacion.direccion.length < 5) {
            throw BusinessException("Ingrese mas de cinco caracteres en la direccion")
        }
        if (centroVacunacion.direccion.length > 50) {
            throw BusinessException("La direccion es demasiado larga")
        }
        if (centroVacunacion.tipo.isEmpty()) {
            throw BusinessException("El tipo de Centro de Vacunacion viene vacio")
        }
        if (centroVacunacion.tipo.length < 7) {
            throw BusinessException("El tipo de Centro de Vacunacion es muy corto")
        }
        if (centroVacunacion.tipo != "Privado" && centroVacunacion.tipo != "Publico") {
            throw BusinessException("Tipo de Centro de Vacunacion Invalido. Indique si es Privado o Publico.")
        }
        if (centroVacunacion.horario.isEmpty()) {
            throw BusinessException("El horario viene vacio")
        }
        if (!centroVacunacion.horario.contains("-")) {
            throw BusinessException("Horario invalido. Ejemplo: Lunes-Viernes 8:00am-5:00pm")
        }
        if (!centroVacunacion.horario.contains(":")) {
            throw BusinessException("Horario invalido. Ejemplo: Lunes-Viernes 8:00am-5:00pm")
        }
        if (!horario(centroVacunacion.horario)) {
            throw BusinessException("Horario invalido. Ejemplo: Lunes-Viernes 8:00am-5:00pm")
        }
        if (centroVacunacion.horario.length > 50) {
            throw BusinessException("El horario es demasiado largo")
        }
        if (centroVacunacion.id_establecimiento.toString().isEmpty()) {
            throw BusinessException("El ID del Establecimiento de Salud no debe estar vacio")
        }
        if (centroVacunacion.id_establecimiento < 0) {
            throw BusinessException("ID de Establecimiento de Salud Invalido")
        }
    }

    private fun horario(horario: String):Boolean {
        val dias = arrayOf("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo")
        var validar = false
        for (dia in dias) {
            if (horario.contains(dia)) {
                validar = true
                break
            }
        }
        return validar
    }
}