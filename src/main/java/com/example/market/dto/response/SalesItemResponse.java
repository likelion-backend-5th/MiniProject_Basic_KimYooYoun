package com.example.market.dto.response;

import com.example.market.entity.SalesItemEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SalesItemResponse {
	String title;
	String description;
	int minPriceWanted;
	String imageUrl;
	String status;

	public static SalesItemResponse fromEntity(SalesItemEntity salesItemEntity){
		return new SalesItemResponse(
			salesItemEntity.getTitle(),
			salesItemEntity.getDescription(),
			salesItemEntity.getMinPrice(),
			salesItemEntity.getImageUrl(),
			salesItemEntity.getStatus().toString()
		);
	}
}
