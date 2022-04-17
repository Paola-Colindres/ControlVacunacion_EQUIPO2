package com.example.ControlVacunacion.demo.business.Comorbilidad

import com.example.ControlVacunacion.demo.dao.Comorbilidad.ComorbilidadRepository
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.Comorbilidad.Comorbilidad
import com.example.ControlVacunacion.demo.model.Vacunas.Vacunas
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.Throws

@Service
class ComorbilidadBusiness: IComorbilidadBusiness {
    @Autowired
    val comorbRepository : ComorbilidadRepository? = null

    @Throws(BusinessException::class)
    override fun getComorbilidades(): List<Comorbilidad> {
        try{
            return comorbRepository!!.findAll()
        }catch (e: Exception){
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getComorbilidadById(idComorb: Long): Comorbilidad {
        val opt: Optional<Comorbilidad>
        try {
            opt = comorbRepository!!.findById(idComorb)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró la Comorbilidad: $idComorb")
        return opt.get()
    }

    @Throws(BusinessException::class)
    override fun saveComorbilidad(comorb: Comorbilidad): Comorbilidad {
        try {
            validarComorbilidad(comorb)
            return comorbRepository!!.save(comorb)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class)
    override fun saveComorbilidades(comorbilidades: List<Comorbilidad>): List<Comorbilidad> {
        try {
            for (comorb in comorbilidades) {
                validarComorbilidad(comorb)
            }
            return comorbRepository!!.saveAll(comorbilidades)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeComorbilidad(idComorb: Long) {
        val opt:Optional<Comorbilidad>
        try {
            opt = comorbRepository!!.findById(idComorb)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró la comorbilidad: $idComorb")
        else {
            try {
                comorbRepository!!.deleteById(idComorb)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun updateComorbilidad(Comorb: Comorbilidad): Comorbilidad {
        val opt:Optional<Comorbilidad>
        try {
            if (Comorb.id_comorbilidad.toString().isEmpty()){
                throw BusinessException("Id de la comorbilidad esta vacía")
            }
            if (Comorb.id_comorbilidad < 0){
                throw BusinessException("Id de comorbilidad Invalida!")
            }
            opt = comorbRepository!!.findById(Comorb.id_comorbilidad)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró la comorbilidad: ${Comorb.id_comorbilidad}")
        else {
            try {
                validarComorbilidad(Comorb)
                val ComorbExist = Comorbilidad(
                        Comorb.nombre,
                        Comorb.tipo)
                ComorbExist.id_comorbilidad = Comorb.id_comorbilidad
                comorbRepository!!.save(ComorbExist)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
        return opt.get()
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getComorbilidadByNombre(nombreComorb: String): Comorbilidad {
        val opt:Optional<Comorbilidad>
        try {
            opt = comorbRepository!!.findFirstByNombre(nombreComorb)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró la comorbilidad: $nombreComorb")
        return opt.get()
    }

    private fun validarComorbilidad(comorb: Comorbilidad) {
        //nombre
        if (comorb.nombre.isEmpty()){
            throw BusinessException("nombre de la comorbilidad no debe estar vacío")
        }
        if (comorb.nombre.length < 3){
            throw BusinessException("Ingrese mas de 3 caracteres en el nombre de la comorbilidad")
        }
        if (comorb.nombre.length > 50){
            throw BusinessException("Ingrese menos de 50 caracteres en el nombre de la comorbilidad")
        }
        //tipo
        if (comorb.tipo.isEmpty()){
            throw BusinessException("tipo de la comorbilidad no debe estar vacío")
        }
        if (comorb.tipo.length < 6){
            throw BusinessException("Ingrese mas de 6 caracteres en el tipo de la comorbilidad")
        }
        if (comorb.tipo.length > 50){
            throw BusinessException("Ingrese menos de 50 caracteres en el tipo de la comorbilidad")
        }

    }
}