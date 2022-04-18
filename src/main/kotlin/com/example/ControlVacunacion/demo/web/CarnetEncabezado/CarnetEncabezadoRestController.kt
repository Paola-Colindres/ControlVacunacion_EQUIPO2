package com.example.ControlVacunacion.demo.web.CarnetEncabezado

import com.example.ControlVacunacion.demo.business.CarnetEncabezadoBusiness.ICarnetEncabezadoBusiness
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.CarnetEncabezado.CarnetEncabezado
import com.example.ControlVacunacion.demo.utils.Constants
import com.example.ControlVacunacion.demo.utils.RestApiError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constants.URL_BASE_CARNET_ENCABEZADO)
class CarnetEncabezadoRestController {
    @Autowired
    val carEncaBusiness: ICarnetEncabezadoBusiness? = null

    @GetMapping("")
    fun list(): ResponseEntity<List<CarnetEncabezado>> {
        return try {
            ResponseEntity(carEncaBusiness!!.getCarnetEncabezado(), HttpStatus.OK)
        }catch (e: BusinessException){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
    @GetMapping("/id/{id}")
    fun loadById(@PathVariable("id") idCarnetEnca: Int):ResponseEntity<Any> {
        return try {
            ResponseEntity(carEncaBusiness!!.getCarnetsEncabezadoById(idCarnetEnca), HttpStatus.OK)
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
    @GetMapping("/numeroCarnet/{numeroCarnet}")
    fun loadByNumeroCarnet(@PathVariable("numeroCarnet") numeroCarnet: Long):ResponseEntity<Any> {
        return try {
            ResponseEntity(carEncaBusiness!!.getCarnetEncabezadoByNumeroCarnet(numeroCarnet), HttpStatus.OK)
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
    @PostMapping("/addCarnetEncabezado")
    fun insert(@RequestBody carnetEnca: CarnetEncabezado):ResponseEntity<Any> {
        return try {
            carEncaBusiness!!.saveCarnetEncabezado(carnetEnca)
            val responseHeader = HttpHeaders()
            responseHeader.set("location", Constants.URL_BASE_CARNET_ENCABEZADO + "/" + carnetEnca.id_carnetEncabezado)
            ResponseEntity(carnetEnca, responseHeader, HttpStatus.CREATED)
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
    @PostMapping("/addCarnetsEncabezado")
    fun insert(@RequestBody carnetsEnca: List<CarnetEncabezado>):ResponseEntity<Any> {
        return try {
            ResponseEntity(carEncaBusiness!!.saveCarnetsEncabezado(carnetsEnca), HttpStatus.CREATED)
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
    fun update(@RequestBody carEnca: CarnetEncabezado):ResponseEntity<Any> {
        return try {
            carEncaBusiness!!.updateCarnetEncabezado(carEnca)
            ResponseEntity(carEnca, HttpStatus.OK)
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
    fun delete(@PathVariable("id") idCarEnca: Int):ResponseEntity<Any> {
        return try {
            carEncaBusiness!!.removeCarnetEncabezado(idCarEnca)
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