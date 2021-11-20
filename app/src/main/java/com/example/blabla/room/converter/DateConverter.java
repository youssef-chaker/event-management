package com.example.blabla.room.converter;

import androidx.room.TypeConverter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static LocalDateTime fromSecondstoLocalDateTime(long seconds){
        return LocalDateTime.now().plusSeconds(seconds);
    }
    @TypeConverter
    public static long fromLocalDateTimeToSeconds(LocalDateTime date){
        return Duration.between(LocalDateTime.now(),date).getSeconds();
    }

}
