package com.kimiega.onlineshop.common.utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

@Throws(JsonProcessingException::class)
fun convertObjectToJson(o: Any?): String {
    if (o == null) {
        return ""
    }
    val mapper = ObjectMapper()
    return mapper.writeValueAsString(o)
}

fun convertJsonToMap(json: String): Map<String, Any> {
    val mapper = ObjectMapper()
    val typeRef = object : TypeReference<HashMap<String, Any>>() {}
    return mapper.readValue(json, typeRef)
}

fun <T> convertJsonToObject(json: String, objectClass : Class<T>): T {
    return jacksonObjectMapper().readValue(json, objectClass)
}