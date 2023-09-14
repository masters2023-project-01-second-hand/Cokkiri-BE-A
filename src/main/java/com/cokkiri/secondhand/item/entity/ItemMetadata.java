package com.cokkiri.secondhand.item.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "ITEM_METADATA")
public class ItemMetadata {

	@Id
	@Column(name = "item_id")
	private Long id;

	@MapsId
	@OneToOne
	@JoinColumn(name = "id")
	private Item item;

	private Long hit;
	private Long chat;
	private Long favorite;

	public ItemMetadata(Item item) {
		this.item = item;
		this.hit = 0L;
		this.chat = 0L;
		this.favorite = 0L;
	}

	public void increaseHit() {
		this.hit++;
	}
}
