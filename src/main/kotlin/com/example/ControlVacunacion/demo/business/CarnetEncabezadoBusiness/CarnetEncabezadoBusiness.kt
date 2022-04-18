package com.example.ControlVacunacion.demo.business.CarnetEncabezadoBusiness

import com.example.ControlVacunacion.demo.dao.CarnetEncabezado.CarnetEncabezadoRepository
import com.example.ControlVacunacion.demo.dao.Civiles.CivilesRepository
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.CarnetEncabezado.CarnetEncabezado
import com.example.ControlVacunacion.demo.model.Civiles.Civiles
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.Throws

@Service
class CarnetEncabezadoBusiness:ICarnetEncabezadoBusiness {
    @Autowired
    val carEncaRepository:CarnetEncabezadoRepository?=null

    @Throws(BusinessException::class)
    override fun getCarnetEncabezado(): List<CarnetEncabezado> {
        try{
            return carEncaRepository!!.findAll()
        }catch (e: Exception){
            throw BusinessException(e.message!!)
        }
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun getCarnetsEncabezadoById(idCarnetEncabezado: Int): CarnetEncabezado {
        val opt: Optional<CarnetEncabezado>
        try {
            opt = carEncaRepository!!.findById(idCarnetEncabezado)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el carnet encabezado: $idCarnetEncabezado")
        return opt.get()
    }
    @Throws(BusinessException::class)
    override fun saveCarnetEncabezado(carnetEncabezado: CarnetEncabezado): CarnetEncabezado {
        try {
            validarCarnetEncabezado(carnetEncabezado)
            return carEncaRepository!!.save(carnetEncabezado)
        } catch (e:BusinessException) {
            throw BusinessException(e.message!!)
        }
        catch (e:NotFoundException) {
            throw NotFoundException(e.message!!)
        }
    }
    @Throws(BusinessException::class)
    override fun saveCarnetsEncabezado(carnetsEncabezado: List<CarnetEncabezado>): List<CarnetEncabezado> {
        try {
            for (carnetEnca in carnetsEncabezado) {
                validarCarnetEncabezado(carnetEnca)
            }
            return carEncaRepository!!.saveAll(carnetsEncabezado)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    override fun removeCarnetEncabezado(idCarnetEncabezado: Int) {
        val opt:Optional<CarnetEncabezado>
        try {
            opt = carEncaRepository!!.findById(idCarnetEncabezado)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el carnet encabezado: $idCarnetEncabezado")
        else {
            try {
                carEncaRepository!!.deleteById(idCarnetEncabezado)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
    }

    override fun updateCarnetEncabezado(carnetEncabezado: CarnetEncabezado): CarnetEncabezado {
        val opt:Optional<CarnetEncabezado>
        try {
            if (carnetEncabezado.id_carnetEncabezado.toString().isEmpty()){
                throw BusinessException("Id del carnet encabezado esta vacío")
            }
            if (carnetEncabezado.id_carnetEncabezado < 0){
                throw BusinessException("Id del carnet encabezado es Invalido!")
            }
            opt = carEncaRepository!!.findById(carnetEncabezado.id_carnetEncabezado)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el carnet encabezado: ${carnetEncabezado.id_carnetEncabezado}")
        else {
            try {
                validarCarnetEncabezado(carnetEncabezado,0)
                val carnetEncaExist = CarnetEncabezado(
                        carnetEncabezado.id_civil,
                        carnetEncabezado.numeroCarnet)
                carnetEncaExist.id_carnetEncabezado = carnetEncabezado.id_carnetEncabezado
                carEncaRepository!!.save(carnetEncaExist)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
        return opt.get()
    }

    override fun getCarnetEncabezadoByNumeroCarnet(numeroCarnet: Long): CarnetEncabezado {
        val opt:Optional<CarnetEncabezado>
        try {
            opt = carEncaRepository!!.findFirstByNumeroCarnet(numeroCarnet)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el carnet encabezado con numero: $numeroCarnet")
        return opt.get()
    }
    private fun validarCarnetEncabezado(carnet: CarnetEncabezado, metodo:Int=1) {
        //id_civil
        if(carnet.id_civil.toString().isEmpty()){
            throw BusinessException("El ID del paciente esta vacío")
        }
        if(carnet.id_civil < 0){
            throw BusinessException("El ID del paciente no puede ser menor a 0")
        }
        if(!validarCivil(carnet.id_civil)){
            throw BusinessException("El ID del paciente no existe")
        }
        //NumeroCarnet
        if(carnet.numeroCarnet.toString().isEmpty()){
            throw BusinessException("El número de carnet esta vacío")
        }
        if(carnet.numeroCarnet <= 0){
            throw BusinessException("El número de carnet no puede ser menor o igual a 0")
        }
        if(carnet.numeroCarnet.toString().length > 5){
            throw BusinessException("El número de carnet es muy largo")
        }
        if(metodo!=0){
            val car:List<CarnetEncabezado> = getCarnetEncabezado()
            for (item in car){
                if(item.numeroCarnet == carnet.numeroCarnet){
                    throw BusinessException("El número de carnet ya esta en uso")
                }
            }
        }
    }
    @Autowired
    val civilRepository : CivilesRepository? = null
    fun validarCivil(idCivil: Long) :Boolean{
        var condicion = false
        val civiles : List<Civiles> = civilRepository!!.findAll()
        for (civil in civiles){
            if (idCivil == civil.id_civil){
                condicion = true
                break
            }
        }
        return condicion
    }
}