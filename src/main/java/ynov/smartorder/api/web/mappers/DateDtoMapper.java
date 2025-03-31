package ynov.smartorder.api.web.mappers;

import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface DateDtoMapper {

    default OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atOffset(ZoneOffset.UTC) : null;
    }

    default LocalDateTime toLocalDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.toLocalDateTime() : null;
    }
}

