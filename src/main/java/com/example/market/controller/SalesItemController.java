package com.example.market.controller;

import com.example.market.dto.request.SalesItemDelteRequest;
import com.example.market.dto.response.Response;
import com.example.market.dto.request.SalesItemRequest;
import com.example.market.dto.response.ResponseMessage;
import com.example.market.service.SalesItemService;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
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

	@PutMapping("/{itemId}/image")
	public Response<String> addImage(
			@PathVariable Long itemId,
			@RequestParam("image") MultipartFile multipartFile,
			@RequestParam("writer") String writer,
			@RequestParam("password") String password) throws IOException
	{
		salesItemService.addImage(itemId, multipartFile, writer, password);

		return new Response<>(ResponseMessage.SUCCESS_ITEM_IMAGE);
	}

	@DeleteMapping("/{itemId}")
	public Response<String> delete(@PathVariable Long itemId, @RequestBody SalesItemDelteRequest request){
		salesItemService.delete(itemId, request.getWriter(), request.getPassword());
		return new Response<>(ResponseMessage.SUCCESS_ITEM_DELETE);
	}

}
