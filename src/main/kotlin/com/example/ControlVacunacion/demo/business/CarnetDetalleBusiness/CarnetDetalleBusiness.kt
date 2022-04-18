package com.example.ControlVacunacion.demo.business.CarnetDetalleBusiness

import com.example.ControlVacunacion.demo.dao.CarnetDetalle.CarnetDetalleRepository
import com.example.ControlVacunacion.demo.dao.CarnetEncabezado.CarnetEncabezadoRepository
import com.example.ControlVacunacion.demo.dao.Empleados.EmpleadosRepository
import com.example.ControlVacunacion.demo.dao.UnidadVacunacion.UnidadVacunacionRepository
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.CarnetDetalle.CarnetDetalle
import com.example.ControlVacunacion.demo.model.CarnetEncabezado.CarnetEncabezado
import com.example.ControlVacunacion.demo.model.Empleados.Empleados
import com.example.ControlVacunacion.demo.model.UnidadVacunacion.UnidadVacunacion
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.Throws

@Service
class CarnetDetalleBusiness:ICarnetDetalleBusiness {
    @Autowired
    val carDeBusiness:CarnetDetalleRepository?=null
    @Throws(BusinessException::class)
    override fun getCarnetDetalle(): List<CarnetDetalle> {
        try{
            return carDeBusiness!!.findAll()
        }catch (e: Exception){
            throw BusinessException(e.message!!)
        }
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun getCarnetsDetalleById(idCarnetDetalle: Int): CarnetDetalle {
        val opt: Optional<CarnetDetalle>
        try {
            opt = carDeBusiness!!.findById(idCarnetDetalle)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el carnet detalle: $idCarnetDetalle")
        return opt.get()
    }
    @Throws(BusinessException::class)
    override fun saveCarnetDetalle(carnetDetalle: CarnetDetalle): CarnetDetalle {
        try {
            validarCarnetDetalle(carnetDetalle)
            return carDeBusiness!!.save(carnetDetalle)
        } catch (e:BusinessException) {
            throw BusinessException(e.message!!)
        }
        catch (e:NotFoundException) {
            throw NotFoundException(e.message!!)
        }
    }
    @Throws(BusinessException::class)
    override fun saveCarnetsDetalle(carnetsDetalle: List<CarnetDetalle>): List<CarnetDetalle> {
        try {
            for (carDetalle in carnetsDetalle) {
                validarCarnetDetalle(carDetalle)
            }
            return carDeBusiness!!.saveAll(carnetsDetalle)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeCarnetDetalle(idCarnetDetalle: Int) {
       val opt:Optional<CarnetDetalle>
        try {
            opt = carDeBusiness!!.findById(idCarnetDetalle)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el carnet detalle: $idCarnetDetalle")
        else {
            try {
                carDeBusiness!!.deleteById(idCarnetDetalle)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun updateCarnetDetalle(carnetDetalle: CarnetDetalle): CarnetDetalle {
        val opt:Optional<CarnetDetalle>
        try {
            if (carnetDetalle.id_carnetDetalle.toString().isEmpty()){
                throw BusinessException("Id del carnet detalle esta vacío")
            }
            if (carnetDetalle.id_carnetDetalle < 0){
                throw BusinessException("Id del carnet detalle Invalido!")
            }
            opt = carDeBusiness!!.findById(carnetDetalle.id_carnetDetalle)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el carnet detalle: ${carnetDetalle.id_carnetDetalle}")
        else {
            try {
                validarCarnetDetalle(carnetDetalle,0)
                val carnetDetalleExist = CarnetDetalle(
                        carnetDetalle.id_carnetEncabezado,
                        carnetDetalle.id_unidad,
                        carnetDetalle.fecha,
                        carnetDetalle.dosis,
                        carnetDetalle.observacion,
                        carnetDetalle.idEmpleadoVacuno)
                carnetDetalleExist.id_carnetDetalle = carnetDetalle.id_carnetDetalle
                carDeBusiness!!.save(carnetDetalle)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
        return opt.get()
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun getCarnetDetalleByObservacion(observacion: String): CarnetDetalle {
        val opt:Optional<CarnetDetalle>
        try {
            opt = carDeBusiness!!.findFirstByObservacion(observacion)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el carnet detalle con observacion: $observacion")
        return opt.get()
    }
    fun validarCarnetDetalle(carnetDetalle: CarnetDetalle,metodo:Int=1){
        if(metodo!=0){
            val detalles:List<CarnetDetalle> = carDeBusiness!!.findAll()
            for(item in detalles){
                if(carnetDetalle.id_carnetDetalle == item.id_carnetDetalle){
                    throw BusinessException("Id del detalle ya esta en uso")
                }
            }
        }
        if (carnetDetalle.id_carnetEncabezado.toString().isEmpty()){
            throw BusinessException("Id del encabezado no debe estar vacío")
        }
        if (carnetDetalle.id_carnetEncabezado < 0){
            throw BusinessException("Id del encabezado Invalido!")
        }
        if (!validarEncabezado(carnetDetalle.id_carnetEncabezado)){
            throw NotFoundException("Id del encabezado no existe")
        }
        if (carnetDetalle.id_unidad.toString().isEmpty()){
            throw BusinessException("Id de la unidad no debe estar vacío")
        }
        if (carnetDetalle.id_unidad < 0){
            throw BusinessException("Id de la unidad Invalido!")
        }
        if (!validarUnidad(carnetDetalle.id_unidad)){
            throw NotFoundException("Id de la unidad no existe")
        }
        if (carnetDetalle.fecha.toString().isEmpty()){
            throw BusinessException("La fecha esta vacía")
        }
        if (carnetDetalle.dosis.isEmpty()) {
            throw BusinessException("La dosis no debe estar vacia")
        }
        if(carnetDetalle.dosis == "Seleccióne una dosis"){
            throw BusinessException("Debe especificar la dosis")
        }
        if (carnetDetalle.dosis.length < 5) {
            throw BusinessException("Ingrese mas de cinco caracteres en la dosis")
        }
        if (carnetDetalle.dosis.length > 50) {
            throw BusinessException("La dosis es demasiado larga")
        }
        if (carnetDetalle.observacion.isEmpty()) {
            throw BusinessException("La observacion no debe estar vacia")
        }
        if (carnetDetalle.observacion.length < 2) {
            throw BusinessException("Ingrese mas de dos caracteres en la observacion")
        }
        if (carnetDetalle.observacion.length > 50) {
            throw BusinessException("La observacion es demasiado larga")
        }
        if (carnetDetalle.idEmpleadoVacuno.toString().isEmpty()){
            throw BusinessException("Id del empleado no debe estar vacío")
        }
        if (carnetDetalle.idEmpleadoVacuno < 0){
            throw BusinessException("Id del empleado Invalido!")
        }
        if (!validarEmpleado(carnetDetalle.idEmpleadoVacuno)){
            throw NotFoundException("Id del empleado no existe")
        }
    }
    @Autowired
    val carEncaRepository : CarnetEncabezadoRepository? = null
    fun validarEncabezado(idCarnet: Int) :Boolean{
        var condicion = false
        val carnet : List<CarnetEncabezado> = carEncaRepository!!.findAll()
        for (item in carnet){
            if (idCarnet == item.id_carnetEncabezado){
                condicion = true
                break
            }
        }
        return condicion
    }
    @Autowired
    val unidadRepository : UnidadVacunacionRepository? = null
    fun validarUnidad(idUnidad: Int) :Boolean{
        var condicion = false
        val unidades : List<UnidadVacunacion> = unidadRepository!!.findAll()
        for (item in unidades){
            if (idUnidad == item.id_unidad.toInt()){
                condicion = true
                break
            }
        }
        return condicion
    }
    @Autowired
    val empleadoRepository : EmpleadosRepository? = null
    fun validarEmpleado(idEmpleados: Int) :Boolean{
        var condicion = false
        val empelados : List<Empleados> = empleadoRepository!!.findAll()
        for (item in empelados){
            if (idEmpleados == item.id_unidad){
                condicion = true
                break
            }
        }
        return condicion
    }
}