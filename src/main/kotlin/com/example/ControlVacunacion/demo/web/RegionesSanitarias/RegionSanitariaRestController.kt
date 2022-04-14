package com.example.ControlVacunacion.demo.web.RegionesSanitarias

import com.example.ControlVacunacion.demo.business.RegionesSanitariasBusiness.IRegionesSanitariasBusiness
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.RegionesSanitarias.RegionesSanitarias
import com.example.ControlVacunacion.demo.utils.Constants
import com.example.ControlVacunacion.demo.utils.RestApiError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constants.URL_BASE_REGIONES)
class RegionSanitariaRestController {

    @Autowired
    val regionBusiness: IRegionesSanitariasBusiness? = null

    @GetMapping("")
    fun list():ResponseEntity<List<RegionesSanitarias>> {
        return try {
            ResponseEntity(regionBusiness!!.getRegionesSanitarias(), HttpStatus.OK)
        } catch (e:BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/id/{id}")
    fun loadById(@PathVariable("id") idRegion: Long):ResponseEntity<Any> {
        return try {
            ResponseEntity(regionBusiness!!.getRegionesSanitariasById(idRegion), HttpStatus.OK)
        } catch (e:BusinessException) {
            val apiError = RestApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:NotFoundException) {
            val apiError = RestApiError(
                    HttpStatus.NOT_FOUND,
                    "No se encontró coincidencias",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/departamento/{departamento}")
    fun loadById(@PathVariable("departamento") departamento: String):ResponseEntity<Any> {
        return try {
            ResponseEntity(regionBusiness!!.getRegionSanitariaByDepartamento(departamento), HttpStatus.OK)
        } catch (e:BusinessException) {
            val apiError = RestApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:NotFoundException) {
            val apiError = RestApiError(
                    HttpStatus.NOT_FOUND,
                    "No se encontró coincidencias",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/addRegionSanitaria")
    fun insert(@RequestBody region: RegionesSanitarias):ResponseEntity<Any> {
        return try {
            regionBusiness!!.saveRegionSanitaria(region)
            val responseHeader = HttpHeaders()
            responseHeader.set("location", Constants.URL_BASE_REGIONES + "/" + region.id_region)
            ResponseEntity(region, responseHeader, HttpStatus.CREATED)
        } catch (e:BusinessException) {
            val apiError = RestApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/addRegionesSanitarias")
    fun insert(@RequestBody regiones: List<RegionesSanitarias>):ResponseEntity<Any> {
        return try {
            ResponseEntity(regionBusiness!!.saveRegionesSanitarias(regiones), HttpStatus.CREATED)
        } catch (e:BusinessException) {
            val apiError = RestApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("")
    fun update(@RequestBody region: RegionesSanitarias):ResponseEntity<Any> {
        return try {
            regionBusiness!!.updateRegionSanitaria(region)
            ResponseEntity(region, HttpStatus.OK)
        } catch (e:BusinessException) {
            val apiError = RestApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:NotFoundException) {
            val apiError = RestApiError(
                    HttpStatus.NOT_FOUND,
                    "No se encontró coincidencias",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") idRegion: Long):ResponseEntity<Any> {
        return try {
            regionBusiness!!.removeRegionSanitaria(idRegion)
            ResponseEntity(HttpStatus.OK)
        } catch (e:BusinessException) {
            val apiError = RestApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        catch (e:NotFoundException) {
            val apiError = RestApiError(
                    HttpStatus.NOT_FOUND,
                    "No se encontró coincidencias",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.NOT_FOUND)
        }
    }
}