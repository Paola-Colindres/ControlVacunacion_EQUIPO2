package com.example.ControlVacunacion.demo.business.Fabricantes

import com.example.ControlVacunacion.demo.dao.Fabricantes.FabricantesRepository
import com.example.ControlVacunacion.demo.dao.Paises.PaisesRepository
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.Fabricantes.fabricantes
import com.example.ControlVacunacion.demo.model.Paises.Paises
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.Throws

@Service
class FabricantesBusiness:IFabricantesBusiness {
    @Autowired
    val fabricanteRepository: FabricantesRepository? = null

    @Throws(BusinessException::class)
    override fun getFabricantes(): List<fabricantes>{
        try{
            return fabricanteRepository!!.findAll()
        }catch (e: Exception){
            throw BusinessException(e.message!!)
        }
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun getFabricanteById(idFabricante: Long): fabricantes {
        val opt: Optional<fabricantes>
        try {
            opt = fabricanteRepository!!.findById(idFabricante)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el fabricante: $idFabricante")
        return opt.get()
    }

    @Throws(BusinessException::class)
    override fun saveFabricante(fabricante: fabricantes): fabricantes {
        try {
            validarfabricantes(fabricante)
            return fabricanteRepository!!.save(fabricante)
        } catch (e:BusinessException) {
            throw BusinessException(e.message!!)
        }
         catch (e:NotFoundException) {
            throw NotFoundException(e.message!!)
         }
    }
    @Throws(BusinessException::class)
    override fun saveFabricantes(fabricantes: List<fabricantes>): List<fabricantes> {
        try {
            for (fabricante in fabricantes) {
                validarfabricantes(fabricante)
            }
            return fabricanteRepository!!.saveAll(fabricantes)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeFabricante(idFabricante: Long) {
        val opt:Optional<fabricantes>
        try {
            opt = fabricanteRepository!!.findById(idFabricante)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el fabricante: $idFabricante")
        else {
            try {
                fabricanteRepository!!.deleteById(idFabricante)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun updateFabricante(fabricante: fabricantes): fabricantes {
        val opt:Optional<fabricantes>
        try {
            if (fabricante.id_fabricante.toString().isEmpty()){
                throw BusinessException("Id del Fabricante esta vacío")
            }
            if (fabricante.id_fabricante < 0){
                throw BusinessException("Id del Fabricante Invalido!")
            }
            opt = fabricanteRepository!!.findById(fabricante.id_fabricante)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el fabricante: ${fabricante.id_fabricante}")
        else {
            try {
                validarfabricantes(fabricante)
                val fabExist = fabricantes(
                        fabricante.laboratorio,
                        fabricante.nombre_contacto,
                        fabricante.telefono_contacto,
                        fabricante.id_pais)
                fabExist.id_fabricante = fabricante.id_fabricante
                fabricanteRepository!!.save(fabExist)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
        return opt.get()
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun getFabrianteByLaboratorio(nombreLab: String): fabricantes {
        val opt:Optional<fabricantes>
        try {
            opt = fabricanteRepository!!.findFirstByLaboratorio(nombreLab)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el fabricante: $nombreLab")
        return opt.get()
    }

    private fun validarfabricantes(fabricante: fabricantes) {
        //laboratorio
        if (fabricante.laboratorio.trim().isEmpty()){
            throw BusinessException("El nombre del laboratorío no debe estar vacío")
        }
        if (fabricante.laboratorio.trim().length < 6){
            throw BusinessException("Ingrese mas de seis caracteres en el nombre del laboratorío")
        }
        if (fabricante.laboratorio.trim().length > 30){
            throw BusinessException("Ingrese menos de 30 caracteres en el nombre del laboratorío")
        }
        //contacto
        if (fabricante.nombre_contacto.trim().isEmpty()){
            throw BusinessException("El nombre de contacto no debe estar vacío")
        }
        if (fabricante.nombre_contacto.trim().length < 3){
            throw BusinessException("Ingrese mas de tres caracteres en el nombre del contacto")
        }
        if (fabricante.nombre_contacto.trim().length > 50){
            throw BusinessException("Ingrese menos de 50 caracteres en el nombre del contacto")
        }
        //telefono_contacto
        if (fabricante.telefono_contacto.toString().isEmpty()) {
            throw BusinessException("El número de tel. del contacto esta vacío")
        }
        if (fabricante.telefono_contacto<= 0){
            throw BusinessException("número de telefono invalido!")
        }
        if (fabricante.telefono_contacto.toString().trim().length < 8){
            throw BusinessException("Ingrese mas de 7 caracteres en Tel. de contacto")
        }
        if (fabricante.telefono_contacto.toString().trim().length > 15){
            throw BusinessException("Ingrese menos de 15 caracteres en Tel. de contacto")
        }
        //id_pais
        if (fabricante.id_pais.toString().trim().isEmpty()){
            throw BusinessException("Id del País esta vacío")
        }
        if (fabricante.id_pais < 0){
            throw BusinessException("Id del País Invalido!")
        }
        if (!validarPais(fabricante.id_pais)){
            throw NotFoundException("ID del País no existe")
        }
    }
    @Autowired
    val paisRepository: PaisesRepository? = null
    fun validarPais(IDPais:Long) : Boolean{
        var condicion :Boolean = false
        var paises : List<Paises>? = paisRepository!!.findAll()
        for (pais in paises!!){
            if (IDPais == pais.id_pais){
                condicion = true
                break
            }
        }
        return condicion
    }
}