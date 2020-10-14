package com.github.marvindaviddiaz.bo;

import javax.persistence.AttributeConverter;
import java.nio.charset.StandardCharsets;

public class BlobToStringConverter implements AttributeConverter<String, byte[]> {
    @Override
    public byte[] convertToDatabaseColumn(String attribute) {
        if (attribute != null) {
            return attribute.getBytes(StandardCharsets.UTF_8);
        } else {
            return null;
        }
    }

    @Override
    public String convertToEntityAttribute(byte[] dbData) {
        if (dbData != null) {
            return new String(dbData, StandardCharsets.UTF_8);
        } else {
            return null;
        }
    }
}
