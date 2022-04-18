package com.example.ControlVacunacion.demo.business.EmpleadosBusiness

import com.example.ControlVacunacion.demo.model.Empleados.Empleados

interface IEmpleadosBusiness {
    fun getEmpleados():List<Empleados>
    fun getEmpleadosById(idEmpleados:Int): Empleados
    fun saveEmpleado(empleado: Empleados): Empleados
    fun saveEmpleados(empleados: List<Empleados>):List<Empleados>
    fun removeEmpleado(idEmpleado: Int)
    fun updateEmpleado(empleado: Empleados): Empleados
    fun getEmpleadoByNombre(nombre: String): Empleados
}