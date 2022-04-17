package com.example.ControlVacunacion.demo.utils

class Constants {
    companion object {
        private const val URL_API_BASE = "/api"
        private const val URL_API_VERSION = "/v1"
        private const val URL_BASE = URL_API_BASE + URL_API_VERSION
        const val URL_BASE_REGIONES = "$URL_BASE/regiones"
        const val URL_BASE_MUNICIPIOS = "$URL_BASE/municipios"
        const val URL_BASE_ESTABLECIMIENTOS_SALUD = "$URL_BASE/establecimientos"
        const val URL_BASE_CENTROS_VACUNACION = "$URL_BASE/centrosVacunacion"
        const val URL_BASE_PAISES = "$URL_BASE/paises"
        //
        const val URL_BASE_FABRICANTES = "$URL_BASE/fabricantes"
        const val URL_BASE_VACUNAS = "$URL_BASE/vacunas"
        const val URL_BASE_CIVILES = "$URL_BASE/civiles"
        const val URL_BASE_COMORBILIDADES= "$URL_BASE/comorbilidad"
        const val URL_BASE_CIVIL_COMORBILIDADES= "$URL_BASE/civil_comorbilidad"
        const val URL_BASE_UNIDADES_VACUNACION = "$URL_BASE/unidadVacunacion"

    }
}