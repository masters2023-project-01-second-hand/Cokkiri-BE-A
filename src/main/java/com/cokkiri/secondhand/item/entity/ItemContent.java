package com.cokkiri.secondhand.item.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "ITEM_CONTENT")
public class ItemContent {

	@Id
	@Column(name = "item_id")
	private Long id;

	@MapsId
	@OneToOne
	@JoinColumn(name = "id")
	private Item item;

	@Lob
	private String content;
}
