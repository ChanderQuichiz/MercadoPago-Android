package com.mercadopago.mapper

import com.mercadopago.models.ServicioFilter


fun ServicioFilter.toQueryMap(): Map<String, String>
{
    return buildMap {
        put("nombre", nombre)
        put("descripcion", descripcion)
        put("estado", estado)
        put("paginator.page", paginator.page.toString())
        put("paginator.size", paginator.size.toString())
    }
}