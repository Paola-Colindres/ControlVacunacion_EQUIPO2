package com.example.ControlVacunacion.demo.business.MunicipiosBusiness

import com.example.ControlVacunacion.demo.dao.Municipios.MunicipiosRepository
import com.example.ControlVacunacion.demo.dao.RegionesSanitarias.RegionesSanitariasRepository
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.Fabricantes.fabricantes
import com.example.ControlVacunacion.demo.model.Municipios.Municipios
import com.example.ControlVacunacion.demo.model.RegionesSanitarias.RegionesSanitarias
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.Throws
import kotlin.reflect.jvm.internal.impl.load.java.BuiltinMethodsWithSpecialGenericSignature

@Service
class MunicipiosBusiness:IMunicipiosBusiness {

    @Autowired
    val municipioRepository: MunicipiosRepository? = null

    @Throws(BusinessException::class)
    override fun getMunicipios(): List<Municipios> {
        try {
            return municipioRepository!!.findAll()
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getMunicipiosById(idMunicipio: Long): Municipios {
        val opt:Optional<Municipios>
        try {
            opt = municipioRepository!!.findById(idMunicipio)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent) {
            throw NotFoundException("No se encontro el Municipio $idMunicipio")
        }
        return opt.get()
    }

    @Throws(BusinessException::class)
    override fun saveMunicipio(municipio: Municipios): Municipios {
        try {
            validarMunicipio(municipio)
            return municipioRepository!!.save(municipio)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class)
    override fun saveMunicipios(municipios: List<Municipios>): List<Municipios> {
        try {
            for (municipio in municipios) {
                validarMunicipio(municipio)
            }
            return municipioRepository!!.saveAll(municipios)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeMunicipio(idMunicipio: Long) {
        val opt:Optional<Municipios>
        try {
            opt = municipioRepository!!.findById(idMunicipio)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontro el Municipio $idMunicipio")
        else {
            try {
                municipioRepository!!.deleteById(idMunicipio)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun updateMunicipio(municipio: Municipios): Municipios {
        val opt:Optional<Municipios>
        try {
            opt = municipioRepository!!.findById(municipio.id_municipio)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontro el Municipio ${municipio.id_municipio}")
        else {
            try {
                validarMunicipio(municipio)
                val municipioExistente = Municipios(
                        municipio.nombre,
                        municipio.id_region)
                municipioExistente.id_municipio = municipio.id_municipio
                municipioRepository!!.save(municipioExistente)
            } catch (e1:Exception) {
                throw BusinessException(e1.message!!)
            }
        }
        return opt.get()
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getMunicipioByNombre(nombreMunicipio: String): Municipios {
        val opt:Optional<Municipios>
        try {
            opt = municipioRepository!!.findFirstByNombre(nombreMunicipio)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent) {
            throw NotFoundException("No se encontro el Municipio $nombreMunicipio")
        }
        return opt.get()
    }

    fun validarMunicipio(municipio: Municipios) {
        if (municipio.nombre.isEmpty()) {
            throw BusinessException("El nombre del municipio no debe estar vacio")
        }
        if (municipio.nombre.length < 4) {
            throw BusinessException("Ingrese mas de cuatro caracteres en el nombre del municipio")
        }
        if (municipio.nombre.length > 50) {
            throw BusinessException("El nombre del municipio es demasiado largo")
        }
        if (municipio.id_region.toString().isEmpty()) {
            throw BusinessException("El Id de Region viene vacio")
        }
        if (municipio.id_region < 0) {
            throw BusinessException("Id de Region invalido")
        }
        if (!validarRegion(municipio.id_region)) {
            throw BusinessException("Id de Region No existe")
        }
    }
    @Autowired
    val regionRepository: RegionesSanitariasRepository? = null
    fun validarRegion(idRegion : Long):Boolean{
        var condicion :Boolean = false
        var regiones : List<RegionesSanitarias>? = regionRepository!!.findAll()
        for (region in regiones!!){
            if (idRegion== region.id_region){
                condicion = true
                break
            }
        }
        return condicion
    }
}
