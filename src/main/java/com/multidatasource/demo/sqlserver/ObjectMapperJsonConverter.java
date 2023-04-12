package com.multidatasource.demo.sqlserver;


import com.google.gson.Gson;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ObjectMapperJsonConverter implements AttributeConverter<Object, String> {
    @Override
    public String convertToDatabaseColumn(Object o) {
        return new Gson().toJson(o);
    }

    @Override
    public Object convertToEntityAttribute(String s) {
        return new Gson().fromJson(s,Object.class);
    }
}
