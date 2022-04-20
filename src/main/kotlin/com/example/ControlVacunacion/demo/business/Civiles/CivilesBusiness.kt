package com.example.ControlVacunacion.demo.business.Civiles

import com.example.ControlVacunacion.demo.dao.Civiles.CivilesRepository
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.Civiles.Civiles
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*
import kotlin.jvm.Throws

@Service
class CivilesBusiness: ICivilesBusiness {
    @Autowired
    val civilRepository : CivilesRepository? = null

    @Throws(BusinessException::class)
    override fun getCiviles(): List<Civiles> {
        try{
            return civilRepository!!.findAll()
        }catch (e: Exception){
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getCivilById(idCivil: Long): Civiles {
        val opt: Optional<Civiles>
        try {
            opt = civilRepository!!.findById(idCivil)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el civil: $idCivil")
        return opt.get()
    }

    @Throws(BusinessException::class)
    override fun saveCivil(civil: Civiles): Civiles {
        try {
            validarCivil(civil)
            return civilRepository!!.save(civil)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class)
    override fun saveCiviles(civiles: List<Civiles>): List<Civiles> {
        try {
            for (civil in civiles) {
                validarCivil(civil)
            }
            return civilRepository!!.saveAll(civiles)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeCivil(idCivil: Long) {
        val opt:Optional<Civiles>
        try {
            opt = civilRepository!!.findById(idCivil)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el civil: $idCivil")
        else {
            try {
                civilRepository!!.deleteById(idCivil)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun updateCivil(civil: Civiles): Civiles {
        val opt:Optional<Civiles>
        try {
            if (civil.id_civil.toString().isEmpty()){
                throw BusinessException("Id del civil esta vacío")
            }
            if (civil.id_civil < 0){
                throw BusinessException("Id del civil Invalido!")
            }
            opt = civilRepository!!.findById(civil.id_civil)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el civil: ${civil.id_civil}")
        else {
            try {
                validarCivil(civil)
                val civilExist = Civiles(
                        civil.dni,
                        civil.nombre,
                        civil.fechaNacimiento,
                        civil.direccion,
                        civil.telefono,
                        civil.sexo)
                civilExist.id_civil = civil.id_civil
                civilRepository!!.save(civilExist)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
        return opt.get()
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getCivilByNombre(nombre: String): Civiles {
        val opt:Optional<Civiles>
        try {
            opt = civilRepository!!.findFirstByNombre(nombre)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el civil: $nombre")
        return opt.get()
    }
    private fun validarCivil(civil: Civiles) {
        //dni
        if (civil.dni.toString().trim().isEmpty()){
            throw BusinessException("DNI no debe estar vacío")
        }
        if (civil.dni.toString().length != 13){
            throw BusinessException("DNI Incorrecto")
        }
        if (civil.dni < 0){
            throw BusinessException("DNI Invalido!")
        }
        //nombre
        if(civil.nombre.trim().isEmpty()){
            throw BusinessException("El nombre del paciente esta vacío")
        }
        if(civil.nombre.trim().length < 3){
            throw BusinessException("Ingrese mas de 3 caracteres en el nombre")
        }
        if(civil.nombre.trim().length > 40){
            throw BusinessException("El nombre es muy largo")
        }
       //fechaNacimiento
        if (civil.fechaNacimiento == null){
            throw BusinessException("La fecha de nacimiento esta vacía")
        }
        if (civil.fechaNacimiento.isAfter(LocalDate.now())){
            throw BusinessException("La fecha de nacimiento es invalida")
        }
        if (civil.fechaNacimiento.isAfter(LocalDate.now().minusYears(12))){
            throw BusinessException("El paciente debe tener mas de 12 años")
        }
        //direccion
        if (civil.direccion.trim().isEmpty()) {
            throw BusinessException("La dirección no debe estar vacia")
        }
        if (civil.direccion.trim().length < 5) {
            throw BusinessException("Ingrese mas de cinco caracteres en la dirección")
        }
        if (civil.direccion.trim().length > 50) {
            throw BusinessException("La dirección es demasiado larga")
        }
        //telefono
        if (civil.telefono.trim().isEmpty()) {
            throw BusinessException("El telefono no debe estar vacío")
        }
        if (civil.telefono.trim().length != 8) {
            throw BusinessException("No. telefono Invalido")
        }
        if (civil.telefono.trim()[0] != '2' && civil.telefono[0] != '9' && civil.telefono[0] != '8' && civil.telefono[0] != '3') {
           throw BusinessException("Operador de telefono Invalido!")
        }
        //sexo
        if (civil.sexo.trim().isEmpty()) {
            throw BusinessException("El sexo del civil no debe estar vacío")
        }
        if (civil.sexo.trim().isEmpty()) {
            throw BusinessException("El sexo del civil no debe estar vacío")
        }
        if (civil.sexo.trim().length < 8) {
            throw BusinessException("Ingrese mas de 7 caracteres en el sexo del civil")
        }
        if (civil.sexo.trim().length > 9) {
            throw BusinessException("Ingrese menos de 9 caracteres en el sexo del civil")
        }

    }
}