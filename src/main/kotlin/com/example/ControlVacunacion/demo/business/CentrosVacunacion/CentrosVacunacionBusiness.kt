package com.example.ControlVacunacion.demo.business.CentrosVacunacion

import com.example.ControlVacunacion.demo.dao.CentrosVacunacion.CentrosVacunacionRepository
import com.example.ControlVacunacion.demo.dao.EstablecimientosDeSalud.EstablecimientosRepository
import com.example.ControlVacunacion.demo.dao.Fabricantes.FabricantesRepository
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.CentrosVacunacion.CentrosVacunacion
import com.example.ControlVacunacion.demo.model.EstablecimientosDeSalud.Establecimientos
import com.example.ControlVacunacion.demo.model.Fabricantes.fabricantes
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
        } catch (e:BusinessException) {
            throw BusinessException(e.message!!)
        }
        catch (e:NotFoundException) {
            throw NotFoundException(e.message!!)
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
            } catch (e:BusinessException) {
                throw BusinessException(e.message!!)
            }
            catch (e:NotFoundException) {
                throw NotFoundException(e.message!!)
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
            throw NotFoundException("No se encontró el Centro de Vacunación: $nombreCentroVacunacion")
        return opt.get()
    }

    private fun validarCentrosVacunacion(centroVacunacion: CentrosVacunacion) {
        if (centroVacunacion.nombre.trim().isEmpty()) {
            throw BusinessException("El nombre del Centro de Vacunación no debe estar vaco")
        }
        if (centroVacunacion.nombre.trim().length < 5) {
            throw BusinessException("Ingrese mas de cinco caracteres en el nombre del Centro de Vacunacion")
        }
        if (centroVacunacion.nombre.trim().length > 50) {
            throw BusinessException("El nombre del Centro de Vacunación es demasiado largo")
        }
        if (centroVacunacion.direccion.trim().isEmpty()) {
            throw BusinessException("La direccion no debe estar vacia")
        }
        if (centroVacunacion.direccion.trim().length < 5) {
            throw BusinessException("Ingrese mas de cinco caracteres en la direccion")
        }
        if (centroVacunacion.direccion.trim().length > 50) {
            throw BusinessException("La direccion es demasiado larga")
        }
        if (centroVacunacion.tipo.trim().isEmpty()) {
            throw BusinessException("El tipo de Centro de Vacunacion viene vacio")
        }
        if (centroVacunacion.tipo.trim().length < 7) {
            throw BusinessException("El tipo de Centro de Vacunacion es muy corto")
        }
        if (centroVacunacion.tipo.trim() != "Privado" && centroVacunacion.tipo != "Publico") {
            throw BusinessException("Tipo de Centro de Vacunacion Invalido. Indique si es Privado o Publico.")
        }
        if (centroVacunacion.horario.trim().isEmpty()) {
            throw BusinessException("El horario viene vacio")
        }
        if (!centroVacunacion.horario.trim().contains("-")) {
            throw BusinessException("Horario invalido. Ejemplo: Lunes-Viernes 8:00am-5:00pm")
        }
        if (!centroVacunacion.horario.trim().contains(":")) {
            throw BusinessException("Horario invalido. Ejemplo: Lunes-Viernes 8:00am-5:00pm")
        }
        if (!horario(centroVacunacion.horario.trim())) {
            throw BusinessException("Horario invalido. Ejemplo: Lunes-Viernes 8:00am-5:00pm")
        }
        if (centroVacunacion.horario.trim().length > 50) {
            throw BusinessException("El horario es demasiado largo")
        }
        if (centroVacunacion.id_establecimiento.toString().trim().isEmpty()) {
            throw BusinessException("El ID del Establecimiento de Salud no debe estar vacio")
        }
        if (centroVacunacion.id_establecimiento < 0) {
            throw BusinessException("ID de Establecimiento de Salud Invalido")
        }
        if (!validarEstab(centroVacunacion.id_establecimiento)) {
            throw NotFoundException("ID de Establecimiento de Salud ${centroVacunacion.id_establecimiento} no encontrado")
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

    @Autowired
    val establecimientoRepository: EstablecimientosRepository? = null
    fun validarEstab(idEstab:Long) : Boolean{
        var condicion :Boolean = false
        var estableciminentos : List<Establecimientos>? = establecimientoRepository!!.findAll()
        for (estab in estableciminentos!!){
            if (idEstab == estab.id_establecimiento){
                condicion = true
                break
            }
        }
        return condicion
    }
}