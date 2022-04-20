package com.example.ControlVacunacion.demo.business.EmpleadosBusiness

import com.example.ControlVacunacion.demo.dao.Cargos.CargosRepository
import com.example.ControlVacunacion.demo.dao.Empleados.EmpleadosRepository
import com.example.ControlVacunacion.demo.dao.UnidadVacunacion.UnidadVacunacionRepository
import com.example.ControlVacunacion.demo.exceptions.BusinessException
import com.example.ControlVacunacion.demo.exceptions.NotFoundException
import com.example.ControlVacunacion.demo.model.Cargos.Cargos
import com.example.ControlVacunacion.demo.model.Empleados.Empleados
import com.example.ControlVacunacion.demo.model.UnidadVacunacion.UnidadVacunacion
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.jvm.Throws


@Service
class EmpleadosBusiness:IEmpleadosBusiness {
    @Autowired
    val empleadoRepository : EmpleadosRepository? =null
    @Throws(BusinessException::class)
    override fun getEmpleados(): List<Empleados> {
        try{
            return empleadoRepository!!.findAll()
        }catch (e: Exception){
            throw BusinessException(e.message!!)
        }
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun getEmpleadosById(idEmpleados: Int): Empleados {
        val opt: Optional<Empleados>
        try {
            opt = empleadoRepository!!.findById(idEmpleados)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el empleado: $idEmpleados")
        return opt.get()
    }
    @Throws(BusinessException::class)
    override fun saveEmpleado(empleado: Empleados): Empleados {
        try {
            validarEmpleado(empleado)
            return empleadoRepository!!.save(empleado)
        } catch (e:BusinessException) {
            throw BusinessException(e.message!!)
        }
        catch (e:NotFoundException) {
            throw NotFoundException(e.message!!)
        }
    }
    @Throws(BusinessException::class)
    override fun saveEmpleados(empleados: List<Empleados>): List<Empleados> {
        try {
            for (item in empleados) {
                validarEmpleado(item)
            }
            return empleadoRepository!!.saveAll(empleados)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun removeEmpleado(idEmpleado: Int) {
        val opt:Optional<Empleados>
        try {
            opt = empleadoRepository!!.findById(idEmpleado)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el empleado: $idEmpleado")
        else {
            try {
                empleadoRepository!!.deleteById(idEmpleado)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun updateEmpleado(empleado: Empleados): Empleados {
        val opt:Optional<Empleados>
        try {
            if (empleado.id_empleado.toString().isEmpty()){
                throw BusinessException("Id del empleado esta vací0")
            }
            if (empleado.id_empleado < 0){
                throw BusinessException("Id del empleado Invalido!")
            }
            opt = empleadoRepository!!.findById(empleado.id_empleado)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el empleado: ${empleado.id_empleado}")
        else {
            try {
                validarEmpleado(empleado,0)
                val empleadoExist = Empleados(
                        empleado.codigo,
                        empleado.dni,
                        empleado.nombre,
                        empleado.telefono,
                        empleado.correo,
                        empleado.password,
                        empleado.id_cargo,
                        empleado.id_unidad)
                empleadoExist.id_empleado = empleado.id_empleado
                empleadoRepository!!.save(empleadoExist)
            } catch (e:Exception) {
                throw BusinessException(e.message!!)
            }
        }
        return opt.get()
    }
    @Throws(BusinessException::class, NotFoundException::class)
    override fun getEmpleadoByNombre(nombre: String): Empleados {
        val opt:Optional<Empleados>
        try {
            opt = empleadoRepository!!.findFirstByNombre(nombre)
        } catch (e:Exception) {
            throw BusinessException(e.message!!)
        }
        if (!opt.isPresent)
            throw NotFoundException("No se encontró el empleado con nombre: $nombre")
        return opt.get()
    }
    fun validarEmpleado(empleado:Empleados,metodo:Int=1){
        //codigo
        if (empleado.codigo.isEmpty()){
            throw BusinessException("Codigo no debe estar vacío")
        }
        if (empleado.codigo.length > 5){
            throw BusinessException("Codigo demasiado largo")
        }
        if (empleado.codigo.length<3){
            throw BusinessException("Codigo demasiado corto")
        }
        if(metodo!=0){
            validarCodigoDNI(empleado)
        }
        //dni
        if (empleado.dni.isEmpty()){
            throw BusinessException("DNI no debe estar vacío")
        }
        if (empleado.dni.length > 13){
            throw BusinessException("DNI demasiado largo")
        }
        if (empleado.dni.length < 13){
            throw BusinessException("DNI demasiado corto")
        }
        //nombre
        if(empleado.nombre.trim().isEmpty()){
            throw BusinessException("El nombre del empleado esta vacío")
        }
        if(empleado.nombre.trim().length < 3){
            throw BusinessException("Ingrese mas de 3 caracteres en el nombre")
        }
        if(empleado.nombre.trim().length > 40){
            throw BusinessException("El nombre es muy largo")
        }
        //telefono
        if (empleado.telefono.toString().trim().isEmpty()) {
            throw BusinessException("El telefono no debe estar vacío")
        }
        if (empleado.telefono.toString().trim().length != 8) {
            throw BusinessException("No. telefono Invalido")
        }
        if (empleado.telefono.toString()[0] != '2' && empleado.telefono.toString()[0] != '9' && empleado.telefono.toString()[0] != '8' && empleado.telefono.toString()[0] != '3') {
            throw BusinessException("Operador de telefono Invalido!")
        }
        val patron = Pattern.compile("[2389]")
        val validarNumero = patron.matcher(java.lang.String.valueOf(empleado.telefono).substring(0, 1))
        if (!validarNumero.matches()) {
            throw BusinessException("El número de teléfono debe iniciar con 2,3,8 o 9")
        }
        //correo
        if(empleado.correo.trim().isEmpty()){
            throw BusinessException("El correo del empleado esta vacío")
        }
        if(empleado.correo.trim().length < 3){
            throw BusinessException("Ingrese mas de 3 caracteres en el correo")
        }
        if(empleado.correo.trim().length > 50) {
            throw BusinessException("El correo es muy largo")
        }
        if (!validarCorreo(empleado.correo)){
            throw BusinessException("La direccion de correo es invalida")
        }
        //password
        if (empleado.password.trim().isEmpty()) {
            throw BusinessException("La contraseña no debe estar vacia")
        }
        if (empleado.password.trim().length < 5) {
            throw BusinessException("Ingrese mas de cinco caracteres en la contraseña")
        }
        if (empleado.password.trim().length > 15) {
            throw BusinessException("La contraseña es demasiado larga")
        }
        if (empleado.id_cargo.toString().trim().isEmpty()){
            throw BusinessException("Id del cargo no debe estar vacío")
        }
        if (empleado.id_cargo < 0){
            throw BusinessException("Id del cargo Invalido!")
        }
        if (!validarCargo(empleado.id_cargo)){
            throw NotFoundException("Id del cargo no existe")
        }
        if (empleado.id_unidad.toString().isEmpty()){
            throw BusinessException("Id de la unidad no debe estar vacío")
        }
        if (empleado.id_unidad < 0){
            throw BusinessException("Id de la unidad Invalido!")
        }
        if (!validarUnidad(empleado.id_unidad)){
            throw NotFoundException("Id de la unidad no existe")
        }
    }
     fun validarCorreo(correo:String):Boolean{
        val  pattern: Pattern = Pattern.compile( "(\\W|^)[\\w.\\-]{3,25}@(hotmail|gmail|ujcv|msn|outlook|yahoo|gmx|zoho|tutonota|protonmail)\\.(com|es|org|edu|hn|uk|co|info|net|site)(\\W|$)")
        val matcher: Matcher = pattern.matcher(correo)

        return matcher.find() //== correo(correo)
    }
    /*fun correo(email:String):Boolean {
        val matcher:Matcher
        val patron = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        val pattern:Pattern = Pattern.compile(patron);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }*/
    fun validarCodigoDNI(empleado:Empleados){
        val lista:List<Empleados> = empleadoRepository!!.findAll()
        for (item in lista){
            if(item.codigo == empleado.codigo){
                throw BusinessException("El codigo ya esta en uso")
            }
            if(item.dni == empleado.dni){
                throw BusinessException("El DNI ya esta en uso")
            }
        }
    }
    @Autowired
    val unidadRepository : UnidadVacunacionRepository? = null
    fun validarUnidad(idUnidad: Int) :Boolean{
        var condicion = false
        val unidades : List<UnidadVacunacion> = unidadRepository!!.findAll()
        for (item in unidades){
            if (idUnidad == item.id_unidad.toInt()){
                condicion = true
                break
            }
        }
        return condicion
    }
    @Autowired
    val cargoRepository : CargosRepository? = null
    fun validarCargo(idCargo: Int) :Boolean{
        var condicion = false
        val unidades : List<Cargos> = cargoRepository!!.findAll()
        for (item in unidades){
            if (idCargo == item.id_cargo){
                condicion = true
                break
            }
        }

        return condicion
    }
}