package com.example.market.service;

import com.example.market.constants.NegotiationStatusType;
import com.example.market.entity.NegotiationEntity;
import com.example.market.entity.SalesItemEntity;
import com.example.market.exception.ApplicationException;
import com.example.market.exception.ErrorCode;
import com.example.market.repository.NegotiationRepository;
import com.example.market.repository.SalesItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

	public void modify(Long itemId, Long proposalId, String requestWriter, String inputPassword, int suggestedPrice, String requestStatus){
		SalesItemEntity savedItem = salesItemRepository.findById(itemId).orElseThrow( () ->
			new ApplicationException(ErrorCode.SALES_ITEM_NOT_FOUND));

		NegotiationEntity savedNego = negotiationRepository.findById(proposalId).orElseThrow( () ->
			new ApplicationException(ErrorCode.NEGOTIATION_NOT_FOUND));

		if(requestWriter.equals(savedItem.getWriter())){//writer가 SalesItem writer인 경우 - 네고 상태 변경
			if(!isValidPassword(inputPassword, savedItem))// 상품 등록자와 같아야 상태를 변경할 수 있음
				throw new ApplicationException(ErrorCode.INVALID_PASSWORD);

			savedNego.updateStatus(requestStatus);

		}else{//writer가 Nego writer인 경우 - 네고 가격 변경
			if(!isValidPassword(inputPassword, savedNego))// 네고 등록자와 같아야 가격을 변경할 수 있음
				throw new ApplicationException(ErrorCode.INVALID_PASSWORD);

			savedNego.updateSuggestedPrice(requestWriter, inputPassword, suggestedPrice);
		}
		negotiationRepository.saveAndFlush(savedNego);
	}
	public void rejectProposals(Long itemId){
		List<NegotiationEntity> rejectedProposals =  negotiationRepository.findAllBySalesItemItemId(itemId);
		rejectedProposals.stream()
			.filter(nego -> nego.getStatus() == NegotiationStatusType.확정)// 확정난 네고는 제외하고
			.forEach(nego -> nego.updateStatus("거절"));// 확정 이외의 status들은 전부 거절로 변경
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
