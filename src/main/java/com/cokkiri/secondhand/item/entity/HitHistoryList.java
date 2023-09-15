package com.cokkiri.secondhand.item.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class HitHistoryList {

	private List<HitHistory> hitHistories;

	public static HitHistoryList from(Item item) {
		List<HitHistory> hitHistories = new ArrayList<>();
		hitHistories.add(new HitHistory(item.getId(), LocalDateTime.now().plusDays(1)));

		return new HitHistoryList(hitHistories);
	}

	public boolean addHitHistoryBy(Item item) {

		if (isHit(item.getId())) {
			return false;
		}

		return hitHistories.add(new HitHistory(item.getId(), LocalDateTime.now().plusDays(1)));
	}

	private boolean isHit(Long itemId) {
		return hitHistories.stream()
			.anyMatch(hitHistory -> hitHistory.getItemId().equals(itemId) && !hitHistory.isExpired());
	}

	public void removeAllExpiredHitHistory() {
		hitHistories.removeIf(HitHistory::isExpired);
	}

}
