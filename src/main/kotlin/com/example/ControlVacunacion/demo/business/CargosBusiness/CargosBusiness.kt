package com.example.ControlVacunacion.demo.business.CargosBusiness

import com.example.ControlVacunacion.demo.dao.Cargos.CargosRepository
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.Cargos.Cargos
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.Throws

@Service
class CargosBusiness: ICargosBusiness {
    @Autowired
    val cargosRepository:CargosRepository?=null

    @Throws(BusinessException::class)
    override fun getCargos(): List<Cargos> {
        try{
            return cargosRepository!!.findAll()
        }catch (e: Exception){
            throw BusinessException(e.message!!)
        }
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun getCargoById(idCargos: Int): Cargos {
        val opt: Optional<Cargos>
        try {
            opt = cargosRepository!!.findById(idCargos)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el cargo: $idCargos")
        return opt.get()
    }
    @Throws(BusinessException::class)
    override fun saveCargo(cargo: Cargos): Cargos {
        try {
            validarCargo(cargo)
            return cargosRepository!!.save(cargo)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }
    @Throws(BusinessException::class)
    override fun saveCargos(cargos: List<Cargos>): List<Cargos> {
        try {
            for (cargo in cargos) {
                validarCargo(cargo)
            }
            return cargosRepository!!.saveAll(cargos)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeCargo(idCargo: Int) {
        val opt:Optional<Cargos>
        try {
            opt = cargosRepository!!.findById(idCargo)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el cargo: $idCargo")
        else {
            try {
                cargosRepository!!.deleteById(idCargo)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun updateCargo(cargo: Cargos): Cargos {
        val opt:Optional<Cargos>
        try {
            if (cargo.id_cargo.toString().isEmpty()){
                throw BusinessException("Id del cargo esta vacío")
            }
            if (cargo.id_cargo < 0){
                throw BusinessException("Id del cargo es Invalido!")
            }
            opt = cargosRepository!!.findById(cargo.id_cargo)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el cargo: ${cargo.id_cargo}")
        else {
            try {
                validarCargo(cargo,0)
                val cargoExist = Cargos(
                        cargo.nombre,
                        cargo.descripcion)
                cargoExist.id_cargo = cargo.id_cargo
                cargosRepository!!.save(cargoExist)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
        return opt.get()
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun getCargoByNombre(nombreCargo: String): Cargos {
        val opt:Optional<Cargos>
        try {
            opt = cargosRepository!!.findFirstByNombre(nombreCargo)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el cargo: $nombreCargo")
        return opt.get()
    }

    private fun validarCargo(cargo: Cargos, metodo:Int=1) {

        //Nombre
        if(cargo.nombre.isEmpty()){
            throw BusinessException("El nombre del cargo esta vacío")
        }
        if(cargo.nombre.length < 3){
            throw BusinessException("Ingrese mas de 3 caracteres en el nombre")
        }
        if(cargo.nombre.length > 30){
            throw BusinessException("El nombre es muy largo")
        }
        if(metodo!=0){
            val cargos:List<Cargos> = getCargos()
            for (item in cargos){
                if(item.nombre == cargo.nombre){
                    throw BusinessException("El nombre del cargo ya esta en uso")
                }
            }
        }
        //Descripcion
        if(cargo.descripcion.isEmpty()){
            throw BusinessException("La descripción esta vacía")
        }
        if(cargo.descripcion.length < 5){
            throw BusinessException("Ingrese mas de 5 caracteres en la descripción")
        }
        if(cargo.descripcion.length > 50){
            throw BusinessException("La descripción es muy larga")
        }
    }
}