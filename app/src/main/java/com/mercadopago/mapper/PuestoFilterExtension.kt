package com.mercadopago.mapper

import com.mercadopago.models.PuestoFilterDto


fun PuestoFilterDto.toQueryMap(): Map<String, String> {
    return buildMap {
        put("codigo", codigo)
        put("descripcion", descripcion)
        put("estado", estado)
        put("paginator.page", paginator.page.toString())
        put("paginator.size", paginator.size.toString())
    }
}