package com.example.market.service;

import com.example.market.constants.NegotiationStatusType;
import com.example.market.entity.NegotiationEntity;
import com.example.market.entity.SalesItemEntity;
import com.example.market.exception.ApplicationException;
import com.example.market.exception.ErrorCode;
import com.example.market.repository.NegotiationRepository;
import com.example.market.repository.SalesItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NegotiationService {

	private final SalesItemRepository salesItemRepository;
	private final NegotiationRepository negotiationRepository;

	public void create(Long itemId, String writer, String password, int suggestedPrice){
		SalesItemEntity savedItem = salesItemRepository.findById(itemId).orElseThrow( () ->
			new ApplicationException(ErrorCode.SALES_ITEM_NOT_FOUND));

		negotiationRepository.save(NegotiationEntity.of(savedItem, suggestedPrice, writer, password, NegotiationStatusType.제안));
	}

}
