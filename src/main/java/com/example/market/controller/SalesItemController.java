package com.example.market.controller;

import com.example.market.dto.response.Response;
import com.example.market.dto.request.SalesItemCreateRequest;
import com.example.market.dto.response.ResponseMessage;
import com.example.market.service.SalesItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class SalesItemController {
	private final SalesItemService salesItemService;

	@PostMapping
	public Response<String> create(@RequestBody SalesItemCreateRequest request) {
		salesItemService.create(
			request.getTitle(),
			request.getDescription(),
			request.getMinPriceWanted(),
			request.getWriter(),
			request.getPassword()
		);
		return new Response<>(ResponseMessage.SUCCESS_ITEM_CREATE);
	}

}
