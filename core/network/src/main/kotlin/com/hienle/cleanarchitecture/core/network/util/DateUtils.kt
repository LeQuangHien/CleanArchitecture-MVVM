package com.hienle.cleanarchitecture.core.network.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.Instant
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class InstantDateDeserializer : JsonDeserializer<Instant> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): Instant? {
        return json?.asString?.let {
            Instant.parse(it)
        }
    }
}

class LocalTimeDeserializer : JsonDeserializer<LocalTime> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): LocalTime? {
        return json?.asString?.let {
            LocalTime.parse(it)
        }
    }
}

class LocalTimeSerializer : JsonSerializer<LocalTime> {
    override fun serialize(
        src: LocalTime,
        typeOfSrc: Type,
        context: JsonSerializationContext,
    ): JsonElement {
        return context.serialize(src.format(DateTimeFormatter.ISO_LOCAL_TIME))
    }
}
