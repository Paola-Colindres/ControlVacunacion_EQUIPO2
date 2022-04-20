package com.example.ControlVacunacion.demo.web.CentrosVacunacion

import com.example.ControlVacunacion.demo.business.CentrosVacunacion.ICentrosVacunacionBusiness
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.CentrosVacunacion.CentrosVacunacion
import com.example.ControlVacunacion.demo.utils.Constants
import com.example.ControlVacunacion.demo.utils.RestApiError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constants.URL_BASE_CENTROS_VACUNACION)
class CentrosVacunacionRestController {

    @Autowired
    val centroBusiness: ICentrosVacunacionBusiness? = null

    @GetMapping("")
    fun list():ResponseEntity<List<CentrosVacunacion>>{
        return try {
            ResponseEntity(centroBusiness!!.getCentrosVacunacion(), HttpStatus.OK)
        } catch (e:BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/id/{id}")
    fun loadById(@PathVariable("id") idCentro: Long):ResponseEntity<Any> {
        return try {
            ResponseEntity(centroBusiness!!.getCentrosVacunacionById(idCentro), HttpStatus.OK)
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

    @GetMapping("/nombre/{nombre}")
    fun loadByNombre(@PathVariable("nombre") nombreCentro: String):ResponseEntity<Any> {
        return try {
            ResponseEntity(centroBusiness!!.getCentroVacunacionByNombre(nombreCentro), HttpStatus.OK)
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

    @PostMapping("/addCentro")
    fun insert(@RequestBody centroVacunacion: CentrosVacunacion):ResponseEntity<Any> {
        return try {
            centroBusiness!!.saveCentroVacunacion(centroVacunacion)
            val responseHeader = HttpHeaders()
            responseHeader.set("location", Constants.URL_BASE_CENTROS_VACUNACION + "/" + centroVacunacion.id_centroVacunacion)
            ResponseEntity(centroVacunacion, responseHeader, HttpStatus.CREATED)
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

    @PostMapping("/addCentros")
    fun insert(@RequestBody centrosVacunacion: List<CentrosVacunacion>):ResponseEntity<Any> {
        return try {
            ResponseEntity(centroBusiness!!.saveCentrosVacunacion(centrosVacunacion), HttpStatus.CREATED)
        } catch (e: BusinessException) {
            val apiError = RestApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("")
    fun update(@RequestBody centroVacunacion: CentrosVacunacion):ResponseEntity<Any> {
        return try {
            centroBusiness!!.updateCentroVacunacion(centroVacunacion)
            ResponseEntity(centroVacunacion, HttpStatus.OK)
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

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") idCentro: Long):ResponseEntity<Any> {
        return try {
            centroBusiness!!.removeCentroVacunacion(idCentro)
            ResponseEntity(HttpStatus.OK)
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







}