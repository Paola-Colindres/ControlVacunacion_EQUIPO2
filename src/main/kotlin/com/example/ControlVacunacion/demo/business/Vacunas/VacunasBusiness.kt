package com.example.ControlVacunacion.demo.business.Vacunas

import com.example.ControlVacunacion.demo.dao.Fabricantes.FabricantesRepository
import com.example.ControlVacunacion.demo.dao.Vacunas.VacunasRepository
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.Fabricantes.fabricantes
import com.example.ControlVacunacion.demo.model.Vacunas.Vacunas
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Service
class VacunasBusiness:IVacunasBusiness {
    @Autowired
    val vacunaRepository : VacunasRepository? = null

    @Throws(BusinessException::class)
    override fun getVacunas(): List<Vacunas> {
        try{
            return vacunaRepository!!.findAll()
        }catch (e: Exception){
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getVacunaById(idVacuna: Long): Vacunas {
        val opt: Optional<Vacunas>
        try {
            opt = vacunaRepository!!.findById(idVacuna)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró la vacuna: $idVacuna")
        return opt.get()
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun saveVacuna(vacuna: Vacunas): Vacunas {
        try {
            validarVacuna(vacuna)
            return vacunaRepository!!.save(vacuna)
        } catch (e:BusinessException) {
            throw BusinessException(e.message!!)
        }
        catch (e:NotFoundException) {
            throw NotFoundException(e.message!!)
        }
    }

    @Throws(BusinessException::class)
    override fun saveVacunas(vacunas: List<Vacunas>): List<Vacunas> {
        try {
            for (vacuna in vacunas) {
                validarVacuna(vacuna)
            }
            return vacunaRepository!!.saveAll(vacunas)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeVacuna(idVacuna: Long) {
        val opt:Optional<Vacunas>
        try {
            opt = vacunaRepository!!.findById(idVacuna)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró la vacuna: $idVacuna")
        else {
            try {
                vacunaRepository!!.deleteById(idVacuna)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun updateVacuna(vacuna: Vacunas): Vacunas {
        val opt:Optional<Vacunas>
        try {
            if (vacuna.id_vacuna.toString().isEmpty()){
                throw BusinessException("Id de la vacuna esta vacía")
            }
            if (vacuna.id_vacuna < 0){
                throw BusinessException("Id de la Vacuna Invalida!")
            }
            opt = vacunaRepository!!.findById(vacuna.id_vacuna)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró la Vacuna: ${vacuna.id_vacuna}")
        else {
            try {
                validarVacuna(vacuna)
                val vacunaExist = Vacunas(
                        vacuna.id_fabricante,
                        vacuna.nombre,
                        vacuna.numeroLote,
                        vacuna.fechaFabricacion,
                        vacuna.fechaVencimiento,
                        vacuna.fechaLlegada)
                vacunaExist.id_vacuna = vacuna.id_vacuna
                vacunaRepository!!.save(vacunaExist)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
        return opt.get()
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun getVacunaByNombre(nombreVacuna: String): Vacunas {
        val opt:Optional<Vacunas>
        try {
            opt = vacunaRepository!!.findFirstByNombre(nombreVacuna)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró la Vacuna: $nombreVacuna")
        return opt.get()
    }
    fun getPattern() = """^\d{4}([\-/.])(0?[1-9]|1[1-2])\1(3[01]|[12][0-9]|0?[1-9])${'$'}"""
    private fun validarVacuna(vacuna: Vacunas) {
        //fabricante
        if (vacuna.id_fabricante.toString().isEmpty()){
            throw BusinessException("ID del fabricante no debe estar vacío")
        }
        if (vacuna.id_fabricante < 0){
            throw BusinessException("ID del fabricante Invalido!")
        }
        if (!validarFab(vacuna.id_fabricante)){
            throw NotFoundException("ID del fabricante no existe")
        }
        //nombre
        if (vacuna.nombre.isEmpty()){
            throw BusinessException("Nombre de la Vacuna no debe estar vacío")
        }
        if (vacuna.nombre.length < 5){
            throw BusinessException("Ingrese mas de 5 caracteres en el nombre")
        }
        if (vacuna.nombre.length > 30 ){
            throw BusinessException("Ingrese menos de 30 caracteres en el nombre")
        }
        //numeroLote
        if (vacuna.numeroLote.toString().isEmpty()){
            throw BusinessException("Numero de Lote esta vacío!")
        }
        if (vacuna.numeroLote <= 0){
            throw BusinessException("Numero de Lote Invalido!")
        }
        if (vacuna.numeroLote.toString().length > 8){
            throw BusinessException("Numero de Lote Invalido!")
        }
        //fecha fabricacion
        if (vacuna.fechaFabricacion == null){
            throw BusinessException("Fecha de fabricación esta vacía!")
        }
        if (vacuna.fechaVencimiento == null){
            throw BusinessException("Fecha de vencimiento esta vacía!")
        }
        if (vacuna.fechaLlegada == null){
            throw BusinessException("Fecha de Llegada esta vacía!")
        }
        if (vacuna.fechaFabricacion!!.isAfter(LocalDate.now())){
            throw BusinessException("Fecha de fabricación Invalida")
        }
        if (vacuna.fechaFabricacion!!.isAfter(vacuna.fechaVencimiento)){
            throw BusinessException("Fecha de fabricación no puede ser mayor a la fecha de vencimiento")
        }
        if (vacuna.fechaVencimiento!!.isBefore(LocalDate.now())){
            throw BusinessException("Fecha de Vencimiento Invalida")
        }
        if (vacuna.fechaLlegada!!.isAfter(LocalDate.now())){
            throw BusinessException("Fecha de Llegada Invalida")
        }
        if (vacuna.fechaLlegada!!.isBefore(vacuna.fechaFabricacion)){
            throw BusinessException("Fecha de Llegada no puede ser menor a la de fabricacion")
        }

    /*
        if (vacuna.fechaFabricacion.toString().length != 10){
            throw BusinessException("Fecha de fabricación invalida!")
        }
        if (vacuna.fechaFabricacion.toString() != 10){
            throw BusinessException("Fecha de fabricación invalida!")
        }
        if (!vacuna.fechaFabricacion.toString().contains("-")){
            throw BusinessException("Fecha de fabricación invalida! ejemplo: 2000-01-09")
        }
        //fecha vencimiento
        if (vacuna.fechaVencimiento.toString().length != 10){
            throw BusinessException("Fecha de vencimiento invalida!")
        }

        if (!vacuna.fechaVencimiento.toString().contains("-")){
            throw BusinessException("Fecha de vencimiento invalida! ejemplo: 2000-01-09")
        }
        //fecha Llegada
        if (vacuna.fechaLlegada.toString().length != 10){
            throw BusinessException("Fecha de Llegada invalida!")
        }
        if (!vacuna.fechaLlegada.toString().contains("-")){
            throw BusinessException("Fecha de Llegada invalida! ejemplo: 2000-01-09")
        }*/
    }
    @Autowired
    val fabricanteRepository: FabricantesRepository? = null
     fun validarFab(IDFab:Long) : Boolean{
        var condicion :Boolean = false
        var fabricantes : List<fabricantes>? = fabricanteRepository!!.findAll()
        for (fab in fabricantes!!){
            if (IDFab == fab.id_fabricante){
               condicion = true
                break
            }
        }
        return condicion
    }


    /*fun obtenerFecha(fecha: LocalDate?):String?{
        val formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd")
        println("voy")
        val formattedString = fecha?.format(formatter)
        println("fui")
        return formattedString
    }*/
}