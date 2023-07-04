package com.example.market.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class SalesItemCreateRequest {
	String title;
	String description;
	int minPriceWanted;
	String writer;
	String password;
}
