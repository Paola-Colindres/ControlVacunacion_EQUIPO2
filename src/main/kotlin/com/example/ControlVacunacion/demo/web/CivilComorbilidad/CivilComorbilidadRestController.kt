package com.example.ControlVacunacion.demo.web.CivilComorbilidad

import com.example.ControlVacunacion.demo.business.CivilComorbilidad.ICivilComorbilidadBusiness
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.Civil_Comorbilidad.Civil_Comorbilidad
import com.example.ControlVacunacion.demo.utils.Constants
import com.example.ControlVacunacion.demo.utils.RestApiError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constants.URL_BASE_CIVIL_COMORBILIDADES)
class CivilComorbilidadRestController {
    @Autowired
    val civComorbBusiness: ICivilComorbilidadBusiness? = null

    @GetMapping("")
    fun list(): ResponseEntity<List<Civil_Comorbilidad>> {
        return try {
            ResponseEntity(civComorbBusiness!!.getCivilesComorbilidades(), HttpStatus.OK)
        }catch (e: BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
    @GetMapping("/id/{id}")
    fun loadById(@PathVariable("id") idCivComorb: Long):ResponseEntity<Any> {
        return try {
            ResponseEntity(civComorbBusiness!!.getCivilComorbilidadById(idCivComorb), HttpStatus.OK)
        } catch (e: BusinessException) {
            val apiError = RestApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e: NotFoundException) {
            val apiError = RestApiError(
                    HttpStatus.NOT_FOUND,
                    "No se encontró coincidencias",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.NOT_FOUND)
        }
    }
    @GetMapping("/estado/{estado}")
    fun loadByEstado(@PathVariable("estado") estado: String):ResponseEntity<Any> {
        return try {
            ResponseEntity(civComorbBusiness!!.getCivilComorbilidadByEstado(estado), HttpStatus.OK)
        } catch (e: BusinessException) {
            val apiError = RestApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e: NotFoundException) {
            val apiError = RestApiError(
                    HttpStatus.NOT_FOUND,
                    "No se encontró coincidencias",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.NOT_FOUND)
        }
    }
    @PostMapping("/addCivilComorbilidad")
    fun insert(@RequestBody civComorb: Civil_Comorbilidad):ResponseEntity<Any> {
        return try {
            civComorbBusiness!!.saveCivilComorbilidad(civComorb)
            val responseHeader = HttpHeaders()
            responseHeader.set("location", Constants.URL_BASE_CIVIL_COMORBILIDADES + "/" + civComorb.id_civilComorbilidad)
            ResponseEntity(civComorb, responseHeader, HttpStatus.CREATED)
        } catch (e: BusinessException) {
            val apiError = RestApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Información enviada no es válida",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e: NotFoundException) {
            val apiError = RestApiError(
                    HttpStatus.NOT_FOUND,
                    "No se encontró coincidencias",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.NOT_FOUND)
        }
    }
    @PostMapping("/addCivilesComorbilidades")
    fun insert(@RequestBody civsComorbs: List<Civil_Comorbilidad>):ResponseEntity<Any> {
        return try {
            ResponseEntity(civComorbBusiness!!.saveCivilesComorbilidades(civsComorbs), HttpStatus.CREATED)
        } catch (e: BusinessException) {
            val apiError = RestApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Información enviada no es válida",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
    @PutMapping("")
    fun update(@RequestBody civComorb: Civil_Comorbilidad):ResponseEntity<Any> {
        return try {
            civComorbBusiness!!.updateCivilComorbilidad(civComorb)
            ResponseEntity(civComorb, HttpStatus.OK)
        } catch (e: BusinessException) {
            val apiError = RestApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Información enviada no es válida",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e: NotFoundException) {
            val apiError = RestApiError(
                    HttpStatus.NOT_FOUND,
                    "No se encontró coincidencias",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.NOT_FOUND)
        }
    }
    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") idCivComorb: Long):ResponseEntity<Any> {
        return try {
            civComorbBusiness!!.removeCivilComorbilidad(idCivComorb)
            ResponseEntity(HttpStatus.OK)
        } catch (e: BusinessException) {
            val apiError = RestApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Información enviada no es válida",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e: NotFoundException) {
            val apiError = RestApiError(
                    HttpStatus.NOT_FOUND,
                    "No se encontró coincidencias",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.NOT_FOUND)
        }
    }
}