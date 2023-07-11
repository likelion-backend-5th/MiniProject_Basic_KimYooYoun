package com.example.market.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NegotiationModifyRequest {
	private String writer;
	private String password;
	private int suggestedPrice;
	private String status;

}
