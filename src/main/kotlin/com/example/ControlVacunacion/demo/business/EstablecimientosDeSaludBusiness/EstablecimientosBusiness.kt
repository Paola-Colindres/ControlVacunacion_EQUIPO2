package com.example.ControlVacunacion.demo.business.EstablecimientosDeSaludBusiness

import com.example.ControlVacunacion.demo.dao.EstablecimientosDeSalud.EstablecimientosRepository
import com.example.ControlVacunacion.demo.dao.Municipios.MunicipiosRepository
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.EstablecimientosDeSalud.Establecimientos
import com.example.ControlVacunacion.demo.model.Fabricantes.fabricantes
import com.example.ControlVacunacion.demo.model.Municipios.Municipios
//import com.sun.org.apache.bcel.internal.generic.IFEQ
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.Throws

@Service
class EstablecimientosBusiness:IEstablecimientosBusiness {

    @Autowired
    val establecimientoRepository: EstablecimientosRepository? = null

    @Throws(BusinessException::class)
    override fun getEstablecimientos(): List<Establecimientos> {
        try {
            return establecimientoRepository!!.findAll()
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getEstablecimientosById(idEstablecimiento: Long): Establecimientos {
        val opt:Optional<Establecimientos>
        try {
            opt = establecimientoRepository!!.findById(idEstablecimiento)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent) {
            throw NotFoundException("No se encontro el Establecimiento de Salud $idEstablecimiento")
        }
        return opt.get()
    }

    @Throws(BusinessException::class)
    override fun saveEstablecimiento(establecimiento: Establecimientos): Establecimientos {
        try {
            validarEstablecimiento(establecimiento)
            return establecimientoRepository!!.save(establecimiento)
        } catch (e:BusinessException) {
            throw BusinessException(e.message!!)
        }
        catch (e:NotFoundException) {
            throw NotFoundException(e.message!!)
        }
    }

    @Throws(BusinessException::class)
    override fun saveEstablecimientos(establecimientos: List<Establecimientos>): List<Establecimientos> {
        try {
            for (establecimiento in establecimientos) {
                validarEstablecimiento(establecimiento)
            }
            return establecimientoRepository!!.saveAll(establecimientos)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeEstablecimiento(idEstablecimiento: Long) {
        val opt:Optional<Establecimientos>
        try {
            opt = establecimientoRepository!!.findById(idEstablecimiento)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontro el Establecimiento de Salud $idEstablecimiento")
        else {
            try {
                establecimientoRepository!!.deleteById(idEstablecimiento)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun updateEstablecimiento(establecimiento: Establecimientos): Establecimientos {
        val opt:Optional<Establecimientos>
        try {
            opt = establecimientoRepository!!.findById(establecimiento.id_establecimiento)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontro el Establecimiento de Salud ${establecimiento.id_establecimiento}")
        else {
            try {
                validarEstablecimiento(establecimiento)
                val establecimientoExistente = Establecimientos(
                        establecimiento.nombre,
                        establecimiento.direccion,
                        establecimiento.telefono,
                        establecimiento.id_municipio)
                establecimientoExistente.id_establecimiento = establecimiento.id_establecimiento
                establecimientoRepository!!.save(establecimientoExistente)
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
    override fun getEstablecimientoByNombre(nombreEstablecimiento: String): Establecimientos {
        val opt:Optional<Establecimientos>
        try {
            opt = establecimientoRepository!!.findFirstByNombre(nombreEstablecimiento)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent) {
            throw NotFoundException("No se encontro el Establecimiento de Salud: $nombreEstablecimiento")
        }
        return opt.get()
    }

    private fun validarEstablecimiento(establecimiento: Establecimientos) {
        if (establecimiento.nombre.trim().isEmpty()) {
            throw BusinessException("El nombre del Establecimiento de Salud no debe estar vacio")
        }
        if (establecimiento.nombre.trim().length < 5) {
            throw BusinessException("Ingrese mas de cinco caracteres en el nombre del Establecimiento de Salud")
        }
        if (establecimiento.nombre.trim().length > 50) {
            throw BusinessException("El nombre del Establecimiento de Salud es demasiado largo")
        }
        if (establecimiento.direccion.trim().isEmpty()) {
            throw BusinessException("La direccion no debe estar vacia")
        }
        if (establecimiento.direccion.trim().length < 5) {
            throw BusinessException("Ingrese mas de cinco caracteres en la direccion")
        }
        if (establecimiento.direccion.trim().length > 50) {
            throw BusinessException("La direccion es demasiado larga")
        }
        if (establecimiento.telefono.toString().trim().isEmpty()) {
            throw BusinessException("El numero de telefono no debe estar vacio")
        }
        if (establecimiento.telefono < 0) {
            throw BusinessException("Numero de telefono invalido")
        }
        if (establecimiento.telefono.toString().trim().length < 8) {
            throw BusinessException("El numero de telefono es muy corto")
        }
        if (establecimiento.telefono.toString().trim().length > 8) {
            throw BusinessException("El numero de telefono es demasiado largo")
        }
        if (establecimiento.telefono.toString().trim()[0] != '3' && establecimiento.telefono.toString().trim()[0] != '2'
                && establecimiento.telefono.toString().trim()[0] != '8' && establecimiento.telefono.toString().trim()[0] != '9') {
            throw BusinessException("Operadora de telefono Invalido!")
        }
        if (establecimiento.id_municipio.toString().trim().isEmpty()) {
            throw BusinessException("El Id del Municipio viene vacio")
        }
        if (establecimiento.id_municipio < 0) {
            throw BusinessException("Id de Municipio invalido")
        }
        if (!validarMuni(establecimiento.id_municipio)) {
            throw NotFoundException("Id de Municipio ${establecimiento.id_municipio} no econtrado")
        }
    }

    @Autowired
    val municipioRepository: MunicipiosRepository? = null
    fun validarMuni(idMuni:Long) : Boolean{
        var condicion :Boolean = false
        var municipios : List<Municipios>? = municipioRepository!!.findAll()
        for (muni in municipios!!){
            if (idMuni == muni.id_municipio){
                condicion = true
                break
            }
        }
        return condicion
    }
}