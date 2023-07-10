package com.example.market.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NegotiationCreateRequest {
	private String writer;
	private String password;
	private int suggestedPrice;

}
