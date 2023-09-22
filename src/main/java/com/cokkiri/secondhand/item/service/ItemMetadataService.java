package com.cokkiri.secondhand.item.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
import com.cokkiri.secondhand.global.exception.list.FavoriteCountFailureException;
import com.cokkiri.secondhand.item.entity.Item;
import com.cokkiri.secondhand.item.entity.ItemMetadata;
import com.cokkiri.secondhand.item.repository.ItemMetadataJpaRepository;
import com.cokkiri.secondhand.item.repository.MemoryItemHitCountRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemMetadataService {

	private final ItemMetadataJpaRepository itemMetadataJpaRepository;
	private final MemoryItemHitCountRepository memoryItemHitCountRepository;

	/**
	 * 조회수 증가. 조회수 증가는 중요한 로직이 아니므로 실패해도 예외를 발생시키지 않고 종료된다.
	 * @param useInfo 유저 정보
	 * @param item 조회수를 증가시킬 item
	 */
	@Transactional
	public void increaseHitCount(UserInfoForJwt useInfo, Item item) {

		int retryCount = 0;
		while(retryCount <= 100) {
			try {
				if (memoryItemHitCountRepository.saveHitHistory(useInfo, item)) {
					ItemMetadata metadata = itemMetadataJpaRepository.findByIdWithOptimisticLock(item.getId())
						.orElse(new ItemMetadata(item));

					metadata.increaseHit();

					itemMetadataJpaRepository.save(metadata);
				}
				break;
			} catch (Exception e) {
				retryCount++;
				sleepThread(50);
			}
		}
	}

	@Transactional
	public void increaseFavoriteCount(Item item) {

		int retryCount = 0;
		while(retryCount <= 100) {
			try {
				ItemMetadata metadata = itemMetadataJpaRepository.findByIdWithOptimisticLock(item.getId())
					.orElse(new ItemMetadata(item));

				metadata.increaseFavorite();

				itemMetadataJpaRepository.save(metadata);

				return;
			} catch (Exception e) {
				retryCount++;
				sleepThread(50);
			}
		}
		throw new FavoriteCountFailureException();
	}

	@Transactional
	public void decreaseFavoriteCount(Item item) {

		int retryCount = 0;
		while(retryCount <= 100) {
			try {
				ItemMetadata metadata = itemMetadataJpaRepository.findByIdWithOptimisticLock(item.getId())
					.orElse(new ItemMetadata(item));

				if (metadata.getFavorite() <= 0L) {
					return;
				}

				metadata.decreaseFavorite();

				itemMetadataJpaRepository.save(metadata);

				return;
			} catch (Exception e) {
				retryCount++;
				sleepThread(50);
			}
		}
		throw new FavoriteCountFailureException();
	}

	private void sleepThread(long millisecond) {
		try {
			Thread.sleep(millisecond);
		} catch (InterruptedException e) {
			return;
		}
	}

}
