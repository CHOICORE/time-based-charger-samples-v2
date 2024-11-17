package me.choicore.samples.charge.infrastructure.jpa

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import me.choicore.samples.charge.domain.core.Timeline

@Converter(autoApply = true)
class TimelineConverter(
    private val objectMapper: ObjectMapper = ObjectMapper(),
) : AttributeConverter<Timeline, String> {
    override fun convertToDatabaseColumn(timeline: Timeline): String = objectMapper.writeValueAsString(timeline)

    override fun convertToEntityAttribute(column: String): Timeline = objectMapper.readValue(column, Timeline::class.java)
}
