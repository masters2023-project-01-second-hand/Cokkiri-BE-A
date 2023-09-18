package com.cokkiri.secondhand.item.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokkiri.secondhand.global.auth.entity.UserInfoForJwt;
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

	@Transactional
	public void increaseHitCount(UserInfoForJwt useInfo, Item item) {

		if (memoryItemHitCountRepository.saveHitHistory(useInfo, item)) {
			ItemMetadata metadata = itemMetadataJpaRepository.findByIdWithPessimisticLock(item.getId())
				.orElse(new ItemMetadata(item));

			metadata.increaseHit();

			itemMetadataJpaRepository.save(metadata);
		}
	}

	@Transactional
	public void increaseFavoriteCount(Item item) {
		ItemMetadata metadata = itemMetadataJpaRepository.findByIdWithPessimisticLock(item.getId())
			.orElse(new ItemMetadata(item));

		metadata.increaseFavorite();

		itemMetadataJpaRepository.save(metadata);
	}

	@Transactional
	public void decreaseFavoriteCount(Item item) {
		ItemMetadata metadata = itemMetadataJpaRepository.findByIdWithPessimisticLock(item.getId())
			.orElse(new ItemMetadata(item));

		if (metadata.getFavorite() <= 0L) {
			return;
		}

		metadata.decreaseFavorite();

		itemMetadataJpaRepository.save(metadata);
	}

}
