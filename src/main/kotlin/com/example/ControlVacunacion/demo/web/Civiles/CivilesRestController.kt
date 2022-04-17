package com.example.ControlVacunacion.demo.web.Civiles

import com.example.ControlVacunacion.demo.business.Civiles.ICivilesBusiness
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.Civiles.Civiles
import com.example.ControlVacunacion.demo.model.Vacunas.Vacunas
import com.example.ControlVacunacion.demo.utils.Constants
import com.example.ControlVacunacion.demo.utils.RestApiError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constants.URL_BASE_CIVILES)
class CivilesRestController {
    @Autowired
    val civBusiness: ICivilesBusiness? = null

    @GetMapping("")
    fun list(): ResponseEntity<List<Civiles>> {
        return try {
            ResponseEntity(civBusiness!!.getCiviles(), HttpStatus.OK)
        }catch (e: BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
    @GetMapping("/id/{id}")
    fun loadById(@PathVariable("id") idCiv: Long):ResponseEntity<Any> {
        return try {
            ResponseEntity(civBusiness!!.getCivilById(idCiv), HttpStatus.OK)
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
    fun loadByNombre(@PathVariable("nombre") nombre: String):ResponseEntity<Any> {
        return try {
            ResponseEntity(civBusiness!!.getCivilByNombre(nombre), HttpStatus.OK)
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
    @PostMapping("/addCivil")
    fun insert(@RequestBody civ: Civiles):ResponseEntity<Any> {
        return try {
            civBusiness!!.saveCivil(civ)
            val responseHeader = HttpHeaders()
            responseHeader.set("location", Constants.URL_BASE_CIVILES + "/" + civ.id_civil)
            ResponseEntity(civ, responseHeader, HttpStatus.CREATED)
        } catch (e: BusinessException) {
            val apiError = RestApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Información enviada no es válida",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
    @PostMapping("/addCiviles")
    fun insert(@RequestBody civs: List<Civiles>):ResponseEntity<Any> {
        return try {
            ResponseEntity(civBusiness!!.saveCiviles(civs), HttpStatus.CREATED)
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
    fun update(@RequestBody civ: Civiles):ResponseEntity<Any> {
        return try {
            civBusiness!!.updateCivil(civ)
            ResponseEntity(civ, HttpStatus.OK)
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
    fun delete(@PathVariable("id") idCiv: Long):ResponseEntity<Any> {
        return try {
            civBusiness!!.removeCivil(idCiv)
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