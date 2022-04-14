package com.example.ControlVacunacion.demo.web.Paises

import com.example.ControlVacunacion.demo.business.Paises.IPaisesBusiness
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.Paises.Paises
import com.example.ControlVacunacion.demo.utils.Constants
import com.example.ControlVacunacion.demo.utils.RestApiError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constants.URL_BASE_PAISES)
class PaisesRestController {

    @Autowired
    val paisesBusiness: IPaisesBusiness? = null

    @GetMapping("")
    fun list():ResponseEntity<List<Paises>> {
        return try {
            ResponseEntity(paisesBusiness!!.getPaises(), HttpStatus.OK)
        } catch (e:Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/id/{id}")
    fun loadById(@PathVariable("id") idPais: Long):ResponseEntity<Any> {
        return try {
            ResponseEntity(paisesBusiness!!.getPaisesById(idPais), HttpStatus.OK)
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
   fun loadByNombre(@PathVariable("nombre") nombrePais: String): ResponseEntity<Any> {
       return try {
           ResponseEntity(paisesBusiness!!.getPaisByNombre(nombrePais), HttpStatus.OK)
       } catch (e: BusinessException) {
           val apiError = RestApiError(
                   HttpStatus.INTERNAL_SERVER_ERROR,
                   "Informacion enviada no es valida",
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

    @PostMapping("/addPais")
    fun insert(@RequestBody pais: Paises):ResponseEntity<Any> {
        return try {
            paisesBusiness!!.savePais(pais)
            val responseHeader = HttpHeaders()
            responseHeader.set("location", Constants.URL_BASE_PAISES + "/" + pais.id_pais)
            ResponseEntity(pais, responseHeader, HttpStatus.CREATED)
        } catch (e:BusinessException) {
            val apiError = RestApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Informacion enviada no es valida",
                    e.message.toString()
            )
            ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/addPaises")
    fun insert(@RequestBody paises: List<Paises>):ResponseEntity<Any> {
        return try {
            ResponseEntity(paisesBusiness!!.savePaises(paises), HttpStatus.CREATED)
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
    fun update(@RequestBody pais: Paises):ResponseEntity<Any> {
        return try {
            paisesBusiness!!.updatePais(pais)
            ResponseEntity(pais, HttpStatus.OK)
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
    fun delete(@PathVariable("id") idPais: Long):ResponseEntity<Any> {
        return try {
            paisesBusiness!!.removePais(idPais)
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