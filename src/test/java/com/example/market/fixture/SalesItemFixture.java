package com.example.market.fixture;

import com.example.market.constants.ItemStatusType;
import com.example.market.entity.SalesItemEntity;

public class SalesItemFixture {
	public static SalesItemEntity get(String title, String description, int minPrice, String writer, String password){
		SalesItemEntity salesItem = SalesItemEntity.of(
			title,
			description,
			minPrice,
			ItemStatusType.ON_SALE,
			writer,
			password
		);
		return salesItem;
	}

}
