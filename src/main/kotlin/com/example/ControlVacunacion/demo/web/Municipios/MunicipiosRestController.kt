package com.example.ControlVacunacion.demo.web.Municipios

import com.example.ControlVacunacion.demo.business.MunicipiosBusiness.IMunicipiosBusiness
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.Municipios.Municipios
import com.example.ControlVacunacion.demo.utils.Constants
import com.example.ControlVacunacion.demo.utils.RestApiError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constants.URL_BASE_MUNICIPIOS)
class MunicipiosRestController {

    @Autowired
    val municipioBusiness: IMunicipiosBusiness? = null

    @GetMapping("")
    fun list():ResponseEntity<List<Municipios>>{
        return try {
            ResponseEntity(municipioBusiness!!.getMunicipios(), HttpStatus.OK)
        } catch (e:BusinessException) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/id/{id}")
    fun loadById(@PathVariable("id") idMunicipio: Long):ResponseEntity<Any> {
        return try {
            ResponseEntity(municipioBusiness!!.getMunicipiosById(idMunicipio),  HttpStatus.OK)
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

    @GetMapping("/nombre/{nombre}")
    fun loadByNombre(@PathVariable("nombre") nombreMunicipio: String):ResponseEntity<Any> {
        return try {
            ResponseEntity(municipioBusiness!!.getMunicipioByNombre(nombreMunicipio), HttpStatus.OK)
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

    @PostMapping("/addMunicipio")
    fun insert(@RequestBody municipio: Municipios):ResponseEntity<Any> {
        return try {
            municipioBusiness!!.saveMunicipio(municipio)
            val responseHeader = HttpHeaders()
            responseHeader.set("location", Constants.URL_BASE_MUNICIPIOS + "/" + municipio.id_municipio)
            ResponseEntity(municipio, responseHeader, HttpStatus.CREATED)
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

    @PostMapping("/addMunicipios")
    fun insert(@RequestBody municipios: List<Municipios>):ResponseEntity<Any> {
        return try {
            ResponseEntity(municipioBusiness!!.saveMunicipios(municipios), HttpStatus.CREATED)
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
    fun update(@RequestBody municipio: Municipios):ResponseEntity<Any> {
        return try {
            municipioBusiness!!.updateMunicipio(municipio)
            ResponseEntity(municipio, HttpStatus.OK)
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
    fun delete(@PathVariable("id") idMunicipio:Long):ResponseEntity<Any> {
        return try {
            municipioBusiness!!.removeMunicipio(idMunicipio)
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