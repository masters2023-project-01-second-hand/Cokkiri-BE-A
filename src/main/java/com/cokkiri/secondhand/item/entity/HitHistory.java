package com.cokkiri.secondhand.item.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HitHistory {

	private Long itemId;
	private LocalDateTime expirationDateTime;

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expirationDateTime);
	}
}
