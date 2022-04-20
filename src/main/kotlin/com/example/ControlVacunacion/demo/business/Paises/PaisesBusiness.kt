package com.example.ControlVacunacion.demo.business.Paises

import com.example.ControlVacunacion.demo.dao.Paises.PaisesRepository
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.Paises.Paises
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.Throws

@Service
class PaisesBusiness:IPaisesBusiness {

    @Autowired
    val paisRepository: PaisesRepository? = null

    @Throws(BusinessException::class)
    override fun getPaises(): List<Paises> {
        try {
            return paisRepository!!.findAll()
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getPaisesById(idPais: Long): Paises {
        val opt:Optional<Paises>
        try {
            opt = paisRepository!!.findById(idPais)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontro el Pais $idPais")
        return opt.get()
    }

    @Throws(BusinessException::class)
    override fun savePais(pais: Paises): Paises {
        try {
            validarPais(pais)
            return paisRepository!!.save(pais)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class)
    override fun savePaises(paises: List<Paises>): List<Paises> {
        try {
            for (pais in paises) {
                validarPais(pais)
            }
            return paisRepository!!.saveAll(paises)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun removePais(idPais: Long) {
        val opt:Optional<Paises>
        try {
            opt = paisRepository!!.findById(idPais)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontro el Pais $idPais")
        else {
            try {
                paisRepository!!.deleteById(idPais)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun updatePais(pais: Paises): Paises {
        val opt:Optional<Paises>
        try {
            opt = paisRepository!!.findById(pais.id_pais)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontro el Pais ${pais.id_pais}")
        else {
            try {
                validarPais(pais)
                val paisExistente = Paises(
                        pais.codigo_iso,
                        pais.nombre,
                        pais.codigo_area)
                paisExistente.id_pais = pais.id_pais
                paisRepository!!.save(paisExistente)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
        return opt.get()
    }

    @Throws(BusinessException::class, NotFoundException::class)
    override fun getPaisByNombre(nombrePais: String): Paises {
        val opt:Optional<Paises>
        try {
            opt = paisRepository!!.findFirstByNombre(nombrePais)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontro el Pais: $nombrePais")
        return opt.get()
    }

    private fun validarPais(pais: Paises) {
        if (pais.codigo_iso.trim().isEmpty()) {
            throw BusinessException("El código ISO viene vacío")
        }
        if (pais.codigo_iso.trim().length < 3) {
            throw BusinessException("El código ISO es muy corto")
        }
        if (pais.codigo_iso.trim().length > 3) {
            throw BusinessException("El código ISO es demasiado largo")
        }
        if (pais.nombre.trim().isEmpty()) {
            throw BusinessException("El nombre del país viene vacío")
        }
        if (pais.nombre.trim().length < 4) {
            throw BusinessException("El nombre del país es muy corto")
        }
        if (pais.nombre.trim().length > 50) {
            throw BusinessException("El nombre del país es demasiado largo")
        }
        if (pais.codigo_area.trim().isEmpty()) {
            throw BusinessException("El código de área no debe estar vacio")
        }
        if (!pais.codigo_area.trim().contains("+")) {
            throw BusinessException("Código de área invalido. Ejemplo: +504")
        }
        if (pais.codigo_area.trim().length < 2) {
            throw BusinessException("El código de área es muy corto")
        }
        if (pais.codigo_area.trim().length > 4) {
            throw BusinessException("El código de área es demasiado largo")
        }

    }
}