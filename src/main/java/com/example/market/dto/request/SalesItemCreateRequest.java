package com.example.market.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SalesItemCreateRequest {
	String title;
	String description;
	int minPriceWanted;
	String writer;
	String password;
}
