package com.cokkiri.secondhand.item.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.item.entity.HitHistoryList;
import com.cokkiri.secondhand.item.entity.Item;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class MemoryItemHitCountRepository {

	private static final Map<UserInfoForJwt, HitHistoryList> hitItemListStorage = new ConcurrentHashMap<>();

	/**
	 * 중고 거래 게시글(Item) 조회 히스토리를 저장한다.
	 * @param userInfo 중고 거래 게시글(Item)을 조회할 회원 정보
	 * @param item 조회할 중고 거래 게시글(Item)
	 * @return 히스토리 저장에 성공하면 ture 반환. 이미 조회한 중고 거래 게시글(Item)이면 저장하지 않고 false 반환
	 */
	public boolean saveHitHistory(UserInfoForJwt userInfo, Item item) {

		if (hitItemListStorage.containsKey(userInfo)) {
			return hitItemListStorage.get(userInfo).addHitHistoryBy(item);
		}

		hitItemListStorage.put(userInfo, HitHistoryList.from(item));
		return true;
	}

	@Scheduled(initialDelay = 1000 * 60 * 60 * 24, fixedDelay = 1000 * 60 * 60 * 24)
	public void removeAllExpiredHitHistory() {

		log.info("removeAllExpiredHitHistory");

		for (UserInfoForJwt userinfo : hitItemListStorage.keySet()) {
			hitItemListStorage.get(userinfo).removeAllExpiredHitHistory();
		}
	}
}
