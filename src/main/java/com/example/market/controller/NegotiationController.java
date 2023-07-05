package com.example.market.controller;

import com.example.market.dto.request.NegotiationDeleteRequest;
import com.example.market.dto.request.NegotiationRequest;
import com.example.market.dto.request.NegotiationStatusRequest;
import com.example.market.dto.response.Response;
import com.example.market.dto.response.ResponseMessage;
import com.example.market.exception.ApplicationException;
import com.example.market.exception.ErrorCode;
import com.example.market.service.NegotiationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	@PutMapping("/{proposalId}")
	public Response<String> modify(@PathVariable Long itemId,
								@PathVariable Long proposalId,
								@RequestBody NegotiationRequest request){
		negotiationService.modify(itemId, proposalId, request.getWriter(), request.getPassword(), request.getSuggestedPrice());
		return Response.success(ResponseMessage.SUCCESS_NEGOTIATION_MODIFY);
	}

	@PutMapping("/{proposalId}/status")// 엔드포인트 중복으로 ambiguous 에러 나서 변경
	public Response<String> updateStatus(@PathVariable Long itemId,
										@PathVariable Long proposalId,
										@RequestBody NegotiationStatusRequest request){
		negotiationService.updateStatus(itemId, proposalId, request.getWriter(), request.getPassword(), request.getStatus());

		switch(request.getStatus()){
			case "확정":
				return Response.success(ResponseMessage.SUCCESS_NEGOTIATION_APPROVED);
			case "수락":
			case "거절":
				return Response.success(ResponseMessage.SUCCESS_NEGOTIATION_STATUS);
		}
		throw new ApplicationException(ErrorCode.INVALID_STATUS);
	}
	@DeleteMapping("/{proposalId}")
	public Response<String> delete(@PathVariable Long proposalId,
								@RequestBody NegotiationDeleteRequest request){
		negotiationService.delete(proposalId, request.getWriter(), request.getPassword());
		return Response.success(ResponseMessage.SUCCESS_NEGOTIATION_DELETE);
	}

}
