package com.example.market.controller;

import com.example.market.dto.request.NegotiationRequest;
import com.example.market.dto.response.Response;
import com.example.market.dto.response.ResponseMessage;
import com.example.market.service.NegotiationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items/{itemId}/proposals")
@RequiredArgsConstructor
@Slf4j
public class NegotiationController {
	private final NegotiationService negotiationService;

	@PostMapping
	public Response<String> create(@PathVariable Long itemId, @RequestBody NegotiationRequest request){
		negotiationService.create(itemId, request.getWriter(), request.getPassword(), request.getSuggestedPrice());
		return Response.success(ResponseMessage.SUCCESS_NEGOTIATION_CREATE);
	}

}
