package com.example.market.controller;

import com.example.market.dto.response.Response;
import com.example.market.dto.request.SalesItemRequest;
import com.example.market.dto.response.ResponseMessage;
import com.example.market.service.SalesItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class SalesItemController {
	private final SalesItemService salesItemService;

	@PostMapping
	public Response<String> create(@Valid @RequestBody SalesItemRequest request) {
		salesItemService.create(
			request.getTitle(),
			request.getDescription(),
			request.getMinPriceWanted(),
			request.getWriter(),
			request.getPassword()
		);
		return new Response<>(ResponseMessage.SUCCESS_ITEM_CREATE);
	}
	@PutMapping("/{itemId}")
	public Response<String> modify(@PathVariable Long itemId, @RequestBody SalesItemRequest request){
		salesItemService.modify(
			itemId,
			request.getTitle(),
			request.getDescription(),
			request.getMinPriceWanted(),
			request.getWriter(),
			request.getPassword()
		);
		return new Response<>(ResponseMessage.SUCCESS_ITEM_MODIFY);
	}

}
