package com.cokkiri.secondhand.item.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

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

	@Version
	private Long version;

	public ItemMetadata(Item item) {
		this.item = item;
		this.hit = 0L;
		this.chat = 0L;
		this.favorite = 0L;
	}

	public void increaseHit() {
		this.hit++;
	}

	public void increaseFavorite() {
		this.favorite++;
	}

	public void decreaseFavorite() {
		if (favorite <= 0) {
			this.favorite = 0L;
			return;
		}
		this.favorite--;
	}
}
