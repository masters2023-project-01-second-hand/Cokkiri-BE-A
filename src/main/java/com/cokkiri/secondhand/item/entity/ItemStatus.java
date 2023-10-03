package com.cokkiri.secondhand.item.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cokkiri.secondhand.item.entity.converter.StatusConverter;

import lombok.Getter;

@Getter
@Entity
@Table(name = "STATUS")
public class ItemStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Convert(converter = StatusConverter.class)
	private Status name;

	public String getStatusName() {
		return name.getName();
	}

	public boolean isSameStatus(ItemStatus itemStatus) {
		return Objects.equals(this.id, itemStatus.getId());
	}
}
