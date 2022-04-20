package com.example.ControlVacunacion.demo.business.RegionesSanitariasBusiness

import com.example.ControlVacunacion.demo.dao.RegionesSanitarias.RegionesSanitariasRepository
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.RegionesSanitarias.RegionesSanitarias
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.Throws

@Service
class RegionesSanitariasBusiness:IRegionesSanitariasBusiness {

    @Autowired
    val regionRepository: RegionesSanitariasRepository? = null

    @Throws(BusinessException::class)
    override fun getRegionesSanitarias(): List<RegionesSanitarias> {
        try {
            return regionRepository!!.findAll()
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getRegionesSanitariasById(idRegion: Long): RegionesSanitarias {
        val opt:Optional<RegionesSanitarias>
        try {
            opt=regionRepository!!.findById(idRegion)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent) {
            throw NotFoundException("No se encontro la Region Sanitaria $idRegion")
        }
        return opt.get()
    }

    @Throws(BusinessException::class)
    override fun saveRegionSanitaria(region: RegionesSanitarias): RegionesSanitarias {
        try {
            validarRegionSanitaria(region)
            return regionRepository!!.save(region)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class)
    override fun saveRegionesSanitarias(regiones: List<RegionesSanitarias>): List<RegionesSanitarias> {
        try {
            for (region in regiones) {
                validarRegionSanitaria(region)
            }
            return regionRepository!!.saveAll(regiones)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeRegionSanitaria(idRegion: Long) {
        val opt:Optional<RegionesSanitarias>
        try {
            opt = regionRepository!!.findById(idRegion)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontro la Region Sanitaria $idRegion")
        else {
            try {
                regionRepository!!.deleteById(idRegion)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun updateRegionSanitaria(region: RegionesSanitarias): RegionesSanitarias {
        val opt:Optional<RegionesSanitarias>
        try {
            opt = regionRepository!!.findById(region.id_region)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontro la Region Sanitaria ${region.id_region}")
        else {
            try {
                validarRegionSanitaria(region)
                val regionExistente = RegionesSanitarias(
                        region.departamento,
                        region.jefatura,
                        region.telefono)
                regionExistente.id_region = region.id_region
                regionRepository!!.save(regionExistente)
            } catch (e1:Exception) {
                throw BusinessException(e1.message!!)
            }
            return opt.get()
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getRegionSanitariaByDepartamento(departamento: String): RegionesSanitarias {
        val opt:Optional<RegionesSanitarias>
        try {
            opt = regionRepository!!.findFirstByDepartamento(departamento)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent) {
            throw NotFoundException("No se encontro la Region Sanitaria de $departamento")
        }
        return opt.get()
    }

    private fun validarRegionSanitaria(region: RegionesSanitarias) {
        if (region.departamento.trim().isEmpty()) {
            throw BusinessException("El nombre del departamento no debe estar vacio")
        }
        if (region.departamento.trim().length < 4) {
            throw BusinessException("El nombre del departamento es muy corto")
        }
        if (region.departamento.trim().length > 20) {
            throw BusinessException("El nombre del departamento es muy largo")
        }
        if (region.jefatura.trim().isEmpty()) {
            throw BusinessException("El nombre de Jefatura Regional viene vacio")
        }
        if (region.jefatura.trim().length < 5) {
            throw BusinessException("Ingrese mas de cinco caracteres en Jefatura Regional")
        }
        if (region.jefatura.trim().length > 30) {
            throw BusinessException("El nombre de Jefatura Regional es muy largo")
        }
        if (region.telefono.toString().trim().isEmpty()) {
            throw BusinessException("El numero de telefono no debe estar vacio")
        }
        if (region.telefono < 0) {
            throw BusinessException("Numero de telefono invalido")
        }
        if (region.telefono.toString().trim().length < 8) {
            throw BusinessException("El numero de telefono es muy corto")
        }
        if (region.telefono.toString().trim().length > 8) {
            throw BusinessException("El numero de telefono es demasiado largo")
        }
        if (region.telefono.toString()[0] != '2' && region.telefono.toString()[0] != '9' && region.telefono.toString()[0] != '8'
                && region.telefono.toString()[0] != '3') {
            throw BusinessException("Operadora del telefono Invalido!")
        }
    }
}