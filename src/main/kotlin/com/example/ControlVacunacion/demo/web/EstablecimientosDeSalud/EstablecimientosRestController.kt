package com.example.ControlVacunacion.demo.web.EstablecimientosDeSalud

import com.example.ControlVacunacion.demo.business.EstablecimientosDeSaludBusiness.IEstablecimientosBusiness
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.EstablecimientosDeSalud.Establecimientos
import com.example.ControlVacunacion.demo.utils.Constants
import com.example.ControlVacunacion.demo.utils.RestApiError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constants.URL_BASE_ESTABLECIMIENTOS_SALUD)
class EstablecimientosRestController {

    @Autowired
    val establecimientosBusiness: IEstablecimientosBusiness? = null

    @GetMapping("")
    fun list():ResponseEntity<List<Establecimientos>>{
        return try {
            ResponseEntity(establecimientosBusiness!!.getEstablecimientos(), HttpStatus.OK)
        } catch (e:Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/id/{id}")
    fun loadById(@PathVariable("id") idEstablecimiento: Long):ResponseEntity<Any> {
        return try {
            ResponseEntity(establecimientosBusiness!!.getEstablecimientosById(idEstablecimiento), HttpStatus.OK)
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
    fun loadByNombre(@PathVariable("nombre") nombreEstablecimiento: String):ResponseEntity<Any> {
        return try {
            ResponseEntity(establecimientosBusiness!!.getEstablecimientoByNombre(nombreEstablecimiento), HttpStatus.OK)
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

    @PostMapping("/addEstablecimiento")
    fun insert(@RequestBody establecimiento: Establecimientos):ResponseEntity<Any> {
        return try {
            establecimientosBusiness!!.saveEstablecimiento(establecimiento)
            val responseHeader = HttpHeaders()
            responseHeader.set("location", Constants.URL_BASE_ESTABLECIMIENTOS_SALUD + "/" + establecimiento.id_establecimiento)
            ResponseEntity(establecimiento, responseHeader, HttpStatus.CREATED)
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

    @PostMapping("/addEstablecimientos")
    fun insert(@RequestBody establecimientos: List<Establecimientos>):ResponseEntity<Any> {
        return try {
            ResponseEntity(establecimientosBusiness!!.saveEstablecimientos(establecimientos), HttpStatus.CREATED)
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
    fun update(@RequestBody establecimiento: Establecimientos):ResponseEntity<Any> {
        return try {
            establecimientosBusiness!!.updateEstablecimiento(establecimiento)
            ResponseEntity(establecimiento, HttpStatus.OK)
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
    fun delete(@PathVariable("id") idEstablecimiento: Long):ResponseEntity<Any> {
        return try {
            establecimientosBusiness!!.removeEstablecimiento(idEstablecimiento)
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