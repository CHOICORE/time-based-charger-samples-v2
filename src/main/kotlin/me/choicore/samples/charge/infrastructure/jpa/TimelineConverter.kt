package me.choicore.samples.charge.infrastructure.jpa

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import me.choicore.samples.charge.domain.Timeline
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Converter(autoApply = true)
class TimelineConverter : AttributeConverter<Timeline, String> {
    private val objectMapper: ObjectMapper = Jackson2ObjectMapperBuilder.json().build()

    override fun convertToDatabaseColumn(timeline: Timeline): String = objectMapper.writeValueAsString(timeline)

    override fun convertToEntityAttribute(column: String): Timeline = objectMapper.readValue(column, Timeline::class.java)
}
