package com.mercadopago.models

fun UserFilterDto.toQueryMap(): Map<String, String> {
    val params = mutableMapOf<String, String>()

    if (query.isNotBlank()) {
        params["query"] = query
    }

    if (role.isNotBlank()) {
        params["role"] = role
    }

    if (status.isNotBlank() && status != "Todos") {
        params["status"] = status
    }

    params["paginator.page"] = page.toString()
    params["paginator.size"] = size.toString()

    return params
}