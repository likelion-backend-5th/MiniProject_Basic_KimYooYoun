package com.example.market.dto.response;

import com.example.market.entity.NegotiationEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NegotiationResponse {
	private Long id;
	private Integer suggestedPrice;
	private String status;

	public static NegotiationResponse fromEntity(NegotiationEntity entity){
		return new NegotiationResponse(
			entity.getNegotiationId(),
			entity.getSuggestedPrice(),
			entity.getStatus().toString()
		);
	}

}
