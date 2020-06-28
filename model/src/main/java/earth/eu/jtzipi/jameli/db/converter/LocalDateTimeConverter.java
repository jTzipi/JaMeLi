/*
 * Copyright (c) 2020 Tim Langhammer
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package earth.eu.jtzipi.jameli.db.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, String> {

    private DateTimeFormatter dateTimeFormatter;

    LocalDateTimeConverter( final String format ) {
        this.dateTimeFormatter = DateTimeFormatter.ofPattern( format );
    }


    public static LocalDateTimeConverter of( String format ) {
        return new LocalDateTimeConverter( Objects.requireNonNull( format ) );
    }

    @Override
    public String convertToDatabaseColumn( LocalDateTime localDateTime ) {
        return dateTimeFormatter.format( localDateTime );
    }

    @Override
    public LocalDateTime convertToEntityAttribute( String dbLocalDateTime ) {
        return LocalDateTime.from( dateTimeFormatter.parse( dbLocalDateTime ) );
    }
}
