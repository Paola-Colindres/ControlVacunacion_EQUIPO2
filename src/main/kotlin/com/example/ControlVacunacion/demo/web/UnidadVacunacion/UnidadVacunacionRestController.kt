package com.example.ControlVacunacion.demo.web.UnidadVacunacion

import com.example.ControlVacunacion.demo.business.UnidadVacunacion.IUnidadVacunacionBusiness
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.Civil_Comorbilidad.Civil_Comorbilidad
import com.example.ControlVacunacion.demo.model.UnidadVacunacion.UnidadVacunacion
import com.example.ControlVacunacion.demo.utils.Constants
import com.example.ControlVacunacion.demo.utils.RestApiError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constants.URL_BASE_UNIDADES_VACUNACION)
class UnidadVacunacionRestController {
    @Autowired
    val unidadBusiness: IUnidadVacunacionBusiness? = null

    @GetMapping("")
    fun list(): ResponseEntity<List<UnidadVacunacion>> {
        return try {
            ResponseEntity(unidadBusiness!!.getUnidades(), HttpStatus.OK)
        }catch (e: BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
    @GetMapping("/id/{id}")
    fun loadById(@PathVariable("id") idUnidad: Long):ResponseEntity<Any> {
        return try {
            ResponseEntity(unidadBusiness!!.getUnidadById(idUnidad), HttpStatus.OK)
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
    @GetMapping("/tipo/{tipo}")
    fun loadByTipo(@PathVariable("tipo") tipo: String):ResponseEntity<Any> {
        return try {
            ResponseEntity(unidadBusiness!!.getUnidadByTipo(tipo), HttpStatus.OK)
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
    @PostMapping("/addUnidad")
    fun insert(@RequestBody unidad: UnidadVacunacion):ResponseEntity<Any> {
        return try {
            unidadBusiness!!.saveUnidad(unidad)
            val responseHeader = HttpHeaders()
            responseHeader.set("location", Constants.URL_BASE_UNIDADES_VACUNACION + "/" + unidad.id_unidad)
            ResponseEntity(unidad, responseHeader, HttpStatus.CREATED)
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
    @PostMapping("/addUnidades")
    fun insert(@RequestBody unidades: List<UnidadVacunacion>):ResponseEntity<Any> {
        return try {
            ResponseEntity(unidadBusiness!!.saveUnidades(unidades), HttpStatus.CREATED)
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
    fun update(@RequestBody uniad: UnidadVacunacion):ResponseEntity<Any> {
        return try {
            unidadBusiness!!.updateUnidad(uniad)
            ResponseEntity(uniad, HttpStatus.OK)
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
    fun delete(@PathVariable("id") idUnidad: Long):ResponseEntity<Any> {
        return try {
            unidadBusiness!!.removeUnidad(idUnidad)
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