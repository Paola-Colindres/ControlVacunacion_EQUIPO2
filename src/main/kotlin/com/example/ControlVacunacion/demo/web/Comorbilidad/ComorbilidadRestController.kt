package com.example.ControlVacunacion.demo.web.Comorbilidad

import com.example.ControlVacunacion.demo.business.Comorbilidad.IComorbilidadBusiness
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.Comorbilidad.Comorbilidad
import com.example.ControlVacunacion.demo.utils.Constants
import com.example.ControlVacunacion.demo.utils.RestApiError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constants.URL_BASE_COMORBILIDADES)
class ComorbilidadRestController {
    @Autowired
    val comorbBusiness: IComorbilidadBusiness? = null

    @GetMapping("")
    fun list(): ResponseEntity<List<Comorbilidad>> {
        return try {
            ResponseEntity(comorbBusiness!!.getComorbilidades(), HttpStatus.OK)
        }catch (e: BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
    @GetMapping("/id/{id}")
    fun loadById(@PathVariable("id") idComorb: Long):ResponseEntity<Any> {
        return try {
            ResponseEntity(comorbBusiness!!.getComorbilidadById(idComorb), HttpStatus.OK)
        } catch (e: BusinessException) {
            val apiError = RestApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Información enviada no es valida",
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
    @GetMapping("/nombre/{nombre}")
    fun loadByNombre(@PathVariable("nombre") nombre: String):ResponseEntity<Any> {
        return try {
            ResponseEntity(comorbBusiness!!.getComorbilidadByNombre(nombre), HttpStatus.OK)
        } catch (e: BusinessException) {
            val apiError = RestApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Información enviada no es valida",
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
    @PostMapping("/addComorbilidad")
    fun insert(@RequestBody comorb: Comorbilidad):ResponseEntity<Any> {
        return try {
            comorbBusiness!!.saveComorbilidad(comorb)
            val responseHeader = HttpHeaders()
            responseHeader.set("location", Constants.URL_BASE_COMORBILIDADES + "/" + comorb.id_comorbilidad)
            ResponseEntity(comorb, responseHeader, HttpStatus.CREATED)
        } catch (e: BusinessException) {
            val apiError = RestApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Información enviada no es válida",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
    @PostMapping("/addComorbilidades")
    fun insert(@RequestBody comorbs: List<Comorbilidad>):ResponseEntity<Any> {
        return try {
            ResponseEntity(comorbBusiness!!.saveComorbilidades(comorbs), HttpStatus.CREATED)
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
    fun update(@RequestBody comorb: Comorbilidad):ResponseEntity<Any> {
        return try {
            comorbBusiness!!.updateComorbilidad(comorb)
            ResponseEntity(comorb, HttpStatus.OK)
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
    fun delete(@PathVariable("id") idComorb: Long):ResponseEntity<Any> {
        return try {
            comorbBusiness!!.removeComorbilidad(idComorb)
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