package com.example.ControlVacunacion.demo.business.CivilComorbilidad

import com.example.ControlVacunacion.demo.dao.CivilComorbilidad.CivilComorbilidadRepository
import com.example.ControlVacunacion.demo.dao.Civiles.CivilesRepository
import com.example.ControlVacunacion.demo.dao.Comorbilidad.ComorbilidadRepository
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.Civil_Comorbilidad.Civil_Comorbilidad
import com.example.ControlVacunacion.demo.model.Civiles.Civiles
import com.example.ControlVacunacion.demo.model.Comorbilidad.Comorbilidad
import org.aspectj.weaver.ast.Not
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.Throws

@Service
class CivilComorbilidadBusiness : ICivilComorbilidadBusiness{
    @Autowired
    val civComorbRepositorty : CivilComorbilidadRepository? =null
    @Throws(BusinessException::class)
    override fun getCivilesComorbilidades(): List<Civil_Comorbilidad> {
        try{
            return civComorbRepositorty!!.findAll()
        }catch (e: Exception){
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getCivilComorbilidadById(idCivilComorb: Long): Civil_Comorbilidad {
        val opt: Optional<Civil_Comorbilidad>
        try {
            opt = civComorbRepositorty!!.findById(idCivilComorb)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el Civil_Comorbilidad: $idCivilComorb")
        return opt.get()
    }

    @Throws(BusinessException::class)
    override fun saveCivilComorbilidad(civilComorb: Civil_Comorbilidad): Civil_Comorbilidad {
        try {
            validarCivilComorbilidad(civilComorb)
            return civComorbRepositorty!!.save(civilComorb)
        } catch (e:BusinessException) {
            throw BusinessException(e.message!!)
        }
        catch (e:NotFoundException) {
            throw NotFoundException(e.message!!)
        }
    }

    @Throws(BusinessException::class)
    override fun saveCivilesComorbilidades(civilesComorbilidades: List<Civil_Comorbilidad>): List<Civil_Comorbilidad> {
        try {
            for (civilComorb in civilesComorbilidades) {
                validarCivilComorbilidad(civilComorb)
            }
            return civComorbRepositorty!!.saveAll(civilesComorbilidades)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeCivilComorbilidad(idCivilComorb: Long) {
        val opt:Optional<Civil_Comorbilidad>
        try {
            opt = civComorbRepositorty!!.findById(idCivilComorb)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el Civil_Comorbilidad: $idCivilComorb")
        else {
            try {
                civComorbRepositorty!!.deleteById(idCivilComorb)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun updateCivilComorbilidad(civilComorb: Civil_Comorbilidad): Civil_Comorbilidad {
        val opt:Optional<Civil_Comorbilidad>
        try {
            if (civilComorb.id_civilComorbilidad.toString().isEmpty()){
                throw BusinessException("Id del Civil_Comorbilidad esta vacía")
            }
            if (civilComorb.id_civilComorbilidad < 0){
                throw BusinessException("Id del Civil_Comorbilidad Invalida!")
            }
            opt = civComorbRepositorty!!.findById(civilComorb.id_civilComorbilidad)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró la comorbilidad: ${civilComorb.id_civilComorbilidad}")
        else {
            try {
                validarCivilComorbilidad(civilComorb)
                val civilComorbExist = Civil_Comorbilidad(
                        civilComorb.id_civil,
                        civilComorb.id_comorbilidad,
                        civilComorb.estado,
                        civilComorb.observacion)
                civilComorbExist.id_civilComorbilidad = civilComorb.id_civilComorbilidad
                civComorbRepositorty!!.save(civilComorbExist)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
        return opt.get()
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getCivilComorbilidadByEstado(estado: String): Civil_Comorbilidad {
        val opt:Optional<Civil_Comorbilidad>
        try {
            opt = civComorbRepositorty!!.findFirstByEstado(estado)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el Civil_Comorbilidad con estado: $estado")
        return opt.get()
    }

    private fun validarCivilComorbilidad(civilComorb: Civil_Comorbilidad) {
        //id_civil
        if (civilComorb.id_civil.toString().isEmpty()){
            throw BusinessException("Id del civil no debe estar vacío")
        }
        if (civilComorb.id_civil < 0){
            throw BusinessException("Id del civil Invalido!")
        }
        if (!validarCivil(civilComorb.id_civil)){
            throw NotFoundException("Id del civil no existe")
        }
        //id_comorbilidad
        if (civilComorb.id_comorbilidad.toString().isEmpty()){
            throw BusinessException("Id de la comorbilidad no debe estar vacío")
        }
        if (civilComorb.id_comorbilidad < 0){
            throw BusinessException("Id de la comorbilidad Invalido!")
        }
        if (!validarComorbilidad(civilComorb.id_comorbilidad)){
            throw NotFoundException("Id de la comorbilidad no existe")
        }
        //estado
        if (civilComorb.estado.isEmpty()){
            throw BusinessException("el estado no debe estar vacío")
        }
        if (civilComorb.estado.length < 4){
            throw BusinessException("Ingrese mas de 3 caracteres en el estado")
        }
        if (civilComorb.estado.length > 15){
            throw BusinessException("Ingrese menos de 15 caracteres en el estado")
        }
        //observacion
        if (civilComorb.observacion.isEmpty()){
            throw BusinessException("la observacion no debe estar vacío")
        }
        if (civilComorb.observacion.length < 6){
            throw BusinessException("Ingrese mas de 5 caracteres en la observacion")
        }
        if (civilComorb.observacion.length > 25){
            throw BusinessException("Ingrese menos de 25 caracteres en la observacion")
        }
    }

    @Autowired
    val comorbRepository : ComorbilidadRepository? = null
    private fun validarComorbilidad(idComorbilidad: Long): Boolean {
        var condicion = false
        var comorbilidades : List<Comorbilidad> = comorbRepository!!.findAll()
        for (comorb in comorbilidades!!){
            if (idComorbilidad == comorb.id_comorbilidad){
                condicion = true
                break
            }
        }
        return condicion
    }

    @Autowired
    val civilRepository : CivilesRepository? = null
    fun validarCivil(idCivil: Long) :Boolean{
        var condicion = false
        var civiles : List<Civiles> = civilRepository!!.findAll()
        for (civil in civiles!!){
            if (idCivil == civil.id_civil){
                condicion = true
                break
            }
        }
        return condicion
    }
}