package com.example.studentsmoney.database;

import androidx.room.TypeConverter;

import com.example.studentsmoney.enums.Currency;
import com.example.studentsmoney.enums.Type;

import java.util.Date;

/*
this is converters for work with room.
There are:
    converters, which convert Date <-> Long (SQLite doesn't support Date type)
    converters, which convert Currency <-> String (SQLite doesn't support Currency type)
 */
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String currencyToString(Currency currency){
        return currency.toString();
    }

    @TypeConverter
    public static Currency StringToCurrency(String currencyName){
        return Currency.valueOf(currencyName);
    }

    @TypeConverter
    public static String typeToString(Type type){
        return type.toString();
    }

    @TypeConverter
    public static Type StringToType(String typeName){
        return Type.valueOf(typeName);
    }
}
