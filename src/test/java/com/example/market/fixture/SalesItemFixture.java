package com.example.market.fixture;

import com.example.market.constraints.ItemStatusType;
import com.example.market.entity.SalesItemEntity;

public class SalesItemFixture {
	public static SalesItemEntity get(String title, String description, int minPrice, String writer, String password){
		SalesItemEntity salesItem = SalesItemEntity.of(
			title,
			description,
			minPrice,
			ItemStatusType.판매중,
			writer,
			password
		);
		return salesItem;
	}

}
