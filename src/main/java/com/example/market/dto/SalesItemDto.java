package com.example.market.dto;

import com.example.market.constants.ItemStatusType;
import com.example.market.entity.SalesItemEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SalesItemDto {
	String title;
	String description;
	int minPriceWanted;
	String imageUrl;
	String status;

	public static SalesItemDto fromEntity(SalesItemEntity salesItemEntity){
		return new SalesItemDto(
			salesItemEntity.getTitle(),
			salesItemEntity.getDescription(),
			salesItemEntity.getMinPrice(),
			salesItemEntity.getImageUrl(),
			salesItemEntity.getStatus().toString()
		);
	}
}
