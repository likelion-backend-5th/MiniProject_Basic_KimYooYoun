package com.example.market.controller;

import com.example.market.dto.request.NegotiationDeleteRequest;
import com.example.market.dto.request.NegotiationCreateRequest;
import com.example.market.dto.request.NegotiationModifyRequest;
import com.example.market.dto.response.NegotiationResponse;
import com.example.market.dto.response.Response;
import com.example.market.constants.ResponseMessage;
import com.example.market.service.NegotiationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items/{itemId}/proposals")
@RequiredArgsConstructor
@Slf4j
public class NegotiationController {
	private final NegotiationService negotiationService;

	@PostMapping
	public Response<String> create(@PathVariable Long itemId, @RequestBody NegotiationCreateRequest request)
	{
		negotiationService.create(itemId, request.getWriter(), request.getPassword(), request.getSuggestedPrice());
		return Response.success(ResponseMessage.SUCCESS_NEGOTIATION_CREATE);
	}

	@GetMapping
	public Response<Page<NegotiationResponse>> getNegotiations(@PathVariable Long itemId,
															@RequestParam String writer,
															@RequestParam String password,
															@RequestParam Integer pageNum)
	{

		return Response.success(negotiationService.getNegotiations(itemId, writer, password, pageNum));
	}

	@PutMapping("/{proposalId}")
	public Response<String> modify(@PathVariable Long itemId,
								@PathVariable Long proposalId,
								@RequestBody NegotiationModifyRequest request)
	{
		negotiationService.modify(itemId, proposalId, request.getWriter(), request.getPassword(), request.getSuggestedPrice(), request.getStatus());

		switch(request.getStatus()){
			case "확정":
				negotiationService.rejectProposals(itemId);// 해당 아이템의 제안들을 거절상태로 변경
				return Response.success(ResponseMessage.SUCCESS_NEGOTIATION_APPROVED);
			case "수락":
			case "거절":
				return Response.success(ResponseMessage.SUCCESS_NEGOTIATION_STATUS);
		}
		return Response.success(ResponseMessage.SUCCESS_NEGOTIATION_MODIFY);
	}

	@DeleteMapping("/{proposalId}")
	public Response<String> delete(@PathVariable Long proposalId,
								@RequestBody NegotiationDeleteRequest request)
	{
		negotiationService.delete(proposalId, request.getWriter(), request.getPassword());
		return Response.success(ResponseMessage.SUCCESS_NEGOTIATION_DELETE);
	}

}
