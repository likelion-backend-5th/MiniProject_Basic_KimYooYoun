package com.example.market.service;

import com.example.market.constants.ItemStatusType;
import com.example.market.entity.SalesItemEntity;
import com.example.market.repository.SalesItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SalesItemService {

	private final SalesItemRepository repository;

	public void create(
			String title,
			String description,
			int minPrice,
			String writer,
			String password
	) {
		repository.save(
			SalesItemEntity.of(title, description, minPrice, ItemStatusType.ON_SALE, writer, password));
	}

}
