package com.example.ControlVacunacion.demo.business.UnidadVacunacion

import com.example.ControlVacunacion.demo.dao.CentrosVacunacion.CentrosVacunacionRepository
import com.example.ControlVacunacion.demo.dao.UnidadVacunacion.UnidadVacunacionRepository
import com.example.ControlVacunacion.demo.dao.Vacunas.VacunasRepository
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.CentrosVacunacion.CentrosVacunacion
import com.example.ControlVacunacion.demo.model.UnidadVacunacion.UnidadVacunacion
import com.example.ControlVacunacion.demo.model.Vacunas.Vacunas
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UnidadVacunacionBusiness: IUnidadVacunacionBusiness {
    @Autowired
    val unidadRepository : UnidadVacunacionRepository? = null

    @Throws(BusinessException::class)
    override fun getUnidades(): List<UnidadVacunacion> {
        try{
            return unidadRepository!!.findAll()
        }catch (e: Exception){
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getUnidadById(idUnidad: Long): UnidadVacunacion {
        val opt: Optional<UnidadVacunacion>
        try {
            opt = unidadRepository!!.findById(idUnidad)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontrĂ³ la unidad: $idUnidad")
        return opt.get()
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun saveUnidad(Unidad: UnidadVacunacion): UnidadVacunacion {
        try {
            validarUnidad(Unidad)
            return unidadRepository!!.save(Unidad)
        } catch (e:BusinessException) {
            throw BusinessException(e.message!!)
        }
        catch (e:NotFoundException) {
            throw NotFoundException(e.message!!)
        }
    }

    @Throws(BusinessException::class)
    override fun saveUnidades(Unidades: List<UnidadVacunacion>): List<UnidadVacunacion> {
        try {
            for (unidad in Unidades) {
                validarUnidad(unidad)
            }
            return unidadRepository!!.saveAll(Unidades)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeUnidad(idUnidad: Long) {
        val opt:Optional<UnidadVacunacion>
        try {
            opt = unidadRepository!!.findById(idUnidad)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontrĂ³ la Unidad: $idUnidad")
        else {
            try {
                unidadRepository!!.deleteById(idUnidad)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun updateUnidad(Unidad: UnidadVacunacion): UnidadVacunacion {
        val opt:Optional<UnidadVacunacion>
        try {
            if (Unidad.id_unidad.toString().isEmpty()){
                throw BusinessException("Id de la unidad esta vacĂ­a")
            }
            if (Unidad.id_unidad < 0){
                throw BusinessException("Id de la unidad Invalida!")
            }
            opt = unidadRepository!!.findById(Unidad.id_unidad)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontrĂ³ la Vacuna: ${Unidad.id_unidad}")
        else {
            try {
                validarUnidad(Unidad)
                val unidadExist = UnidadVacunacion(
                        Unidad.id_centro,
                        Unidad.id_vacuna_suministrar,
                        Unidad.tipo)
                unidadExist.id_unidad= Unidad.id_unidad
                unidadRepository!!.save(unidadExist)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
        return opt.get()
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getUnidadByTipo(tipoUnidad: String): UnidadVacunacion {
        val opt:Optional<UnidadVacunacion>
        try {
            opt = unidadRepository!!.findFirstByTipo(tipoUnidad)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontrĂ³ la unidad de tipo: $tipoUnidad")
        return opt.get()
    }

    private fun validarUnidad(unidad: UnidadVacunacion) {
        //centro
        if (unidad.id_centro.toString().trim().isEmpty()){
            throw BusinessException("ID del centro de vacunaciĂ³n no debe estar vacĂ­o")
        }
        if (unidad.id_centro < 0){
            throw BusinessException("ID del centro de vacunaciĂ³n Invalido!")
        }
        if (!validarCentro(unidad.id_centro)){
            throw NotFoundException("ID del Centro de VacunaciĂ³n no existe")
        }
        //vacunaSuministrar
        if (unidad.id_vacuna_suministrar.toString().trim().isEmpty()){
            throw BusinessException("ID de la Vacuna a sumistrar no debe estar vacĂ­o")
        }
        if (unidad.id_vacuna_suministrar < 0){
            throw BusinessException("ID de la Vacuna a sumistrar Invalido!")
        }
        if (!validarVacuna(unidad.id_vacuna_suministrar)){
            throw NotFoundException("ID de la Vacuna a sumistrar ${unidad.id_vacuna_suministrar} no encontrado")
        }
        //tipo
        if (unidad.tipo.trim().isEmpty()){
            throw BusinessException("tipo de unidad no debe estar vacĂ­o")
        }
        if (unidad.tipo.trim().length < 6){
            throw BusinessException("Ingrese mas de 6 caracteres en el tipo de unidad")
        }
        if (unidad.tipo.trim().length > 15){
            throw BusinessException("Ingrese menos de 15 caracteres en el tipo de unidad")
        }
    }

    @Autowired
    val centroRepository : CentrosVacunacionRepository? = null
    private fun validarCentro(idCentro: Long): Boolean {
        var condicion : Boolean = false
        var centros: List<CentrosVacunacion>? = centroRepository!!.findAll()
        for (centro in centros!!){
            if (idCentro == centro.id_centroVacunacion){
                condicion = true
                break
            }
        }
        return condicion
    }
    @Autowired
    val vacunaRepository : VacunasRepository? = null
    private fun validarVacuna(idVacuna: Long): Boolean {
        var condicion : Boolean = false
        var vacunas: List<Vacunas>? = vacunaRepository!!.findAll()
        for (vacuna in vacunas!!){
            if (idVacuna == vacuna.id_vacuna){
                condicion = true
                break
            }
        }
        return condicion
    }

}