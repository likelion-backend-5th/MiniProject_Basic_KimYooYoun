package com.example.market.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SalesItemRequest {
	String title;
	String description;
	int minPriceWanted;
	String writer;
	String password;
}
