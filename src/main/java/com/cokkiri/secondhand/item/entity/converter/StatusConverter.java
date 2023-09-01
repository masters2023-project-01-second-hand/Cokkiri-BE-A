package com.cokkiri.secondhand.item.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.cokkiri.secondhand.item.entity.Status;

@Converter
public class StatusConverter implements AttributeConverter<Status, String> {

	@Override
	public String convertToDatabaseColumn(Status attribute) {
		if (attribute == null) {
			throw new IllegalArgumentException("Status가 null입니다.");
		}

		return attribute.getName();
	}

	@Override
	public Status convertToEntityAttribute(String dbData) {
		return Status.parseBy(dbData);
	}
}
