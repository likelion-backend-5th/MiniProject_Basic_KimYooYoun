package com.example.market.service;

import com.example.market.constants.ItemStatusType;
import com.example.market.constants.NegotiationStatusType;
import com.example.market.dto.response.Response;
import com.example.market.dto.response.ResponseMessage;
import com.example.market.entity.CommentEntity;
import com.example.market.entity.NegotiationEntity;
import com.example.market.entity.SalesItemEntity;
import com.example.market.exception.ApplicationException;
import com.example.market.exception.ErrorCode;
import com.example.market.repository.NegotiationRepository;
import com.example.market.repository.SalesItemRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.http11.filters.SavedRequestInputFilter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NegotiationService {

	private final SalesItemRepository salesItemRepository;
	private final NegotiationRepository negotiationRepository;

	public void create(Long itemId, String writer, String password, int suggestedPrice){
		SalesItemEntity savedItem = salesItemRepository.findById(itemId).orElseThrow( () ->
			new ApplicationException(ErrorCode.SALES_ITEM_NOT_FOUND));

		negotiationRepository.save(NegotiationEntity.of(savedItem, suggestedPrice, writer, password, NegotiationStatusType.제안));
	}

	public void modify(Long itemId, Long proposalId, String writer, String inputPassword, int suggestedPrice){
		SalesItemEntity savedItem = salesItemRepository.findById(itemId).orElseThrow( () ->
			new ApplicationException(ErrorCode.SALES_ITEM_NOT_FOUND));

		NegotiationEntity savedNego = negotiationRepository.findById(proposalId).orElseThrow( () ->
			new ApplicationException(ErrorCode.NEGOTIATION_NOT_FOUND));

		if(!isValidPassword(inputPassword, savedNego))// 네고 등록자만 네고 내용을 수정할 수 있음
			throw new ApplicationException(ErrorCode.INVALID_PASSWORD);

		savedNego.updateNegotiation(writer, inputPassword, suggestedPrice);
		negotiationRepository.saveAndFlush(savedNego);
	}
	public void updateStatus(Long itemId, Long proposalId, String writer, String inputPassword, String status){
		SalesItemEntity savedItem = salesItemRepository.findById(itemId).orElseThrow( () ->
			new ApplicationException(ErrorCode.SALES_ITEM_NOT_FOUND));

		NegotiationEntity savedNego = negotiationRepository.findById(proposalId).orElseThrow( () ->
			new ApplicationException(ErrorCode.NEGOTIATION_NOT_FOUND));

		if(!isValidPassword(inputPassword, savedItem))// 상품 등록자만 네고 상태를 변경할 수 있음
			throw new ApplicationException(ErrorCode.INVALID_PASSWORD);

		log.info(status);
		switch(status){
			case "확정":
				savedItem.updateStatus(ItemStatusType.판매완료);
				rejectProposals(savedItem.getItemId());// 해당 상품으로 등록되어있는 제안들 전부 거절하기
				savedNego.updateStatus(NegotiationStatusType.확정); // 확정한 상품만 거절에서 확정으로 변경
				break;
			case "수락":
				savedNego.updateStatus(NegotiationStatusType.수락);
				break;
			case "거절":
				savedNego.updateStatus(NegotiationStatusType.거절);
				break;
		}
		negotiationRepository.saveAndFlush(savedNego);
	}
	private void rejectProposals(Long itemId){
		List<NegotiationEntity> rejectedProposals =  negotiationRepository.findAllBySalesItemItemId(itemId);
		rejectedProposals.stream()
			.forEach(nego -> nego.updateStatus(NegotiationStatusType.거절));
		negotiationRepository.saveAllAndFlush(rejectedProposals);
	}
	public void delete(Long proposalId, String writer, String inputPassword){
		NegotiationEntity savedNego = negotiationRepository.findById(proposalId).orElseThrow( () ->
			new ApplicationException(ErrorCode.NEGOTIATION_NOT_FOUND));

		if(!isValidPassword(inputPassword, savedNego))
			throw new ApplicationException(ErrorCode.INVALID_PASSWORD);

		negotiationRepository.delete(savedNego);
	}

	private boolean isValidPassword(String inputPassword, NegotiationEntity savedItem){
		return inputPassword.equals(savedItem.getPassword());
	}
	private boolean isValidPassword(String inputPassword, SalesItemEntity savedItem){
		return inputPassword.equals(savedItem.getPassword());
	}

}
