package com.example.market.service;

import com.example.market.constraints.NegotiationStatusType;
import com.example.market.dto.response.NegotiationResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static com.example.market.util.ServiceUtils.isValidPassword;

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
	public Page<NegotiationResponse> getNegotiations(Long itemId, String requestWriter, String password, Integer pageNum) {
		SalesItemEntity savedItem = salesItemRepository.findById(itemId).orElseThrow( () ->
			new ApplicationException(ErrorCode.SALES_ITEM_NOT_FOUND));

		Pageable pageable = PageRequest.of(pageNum - 1, 25);

		if(requestWriter.equals(savedItem.getWriter())){// 상품 등록자가 요청 온 제안들을 모두 확인하는 경우
			if(!isValidPassword(password, savedItem))//
				throw new ApplicationException(ErrorCode.INVALID_PASSWORD);

			return negotiationRepository.findAllBySalesItem_ItemId(itemId, pageable).map(NegotiationResponse::fromEntity);
		}else{// 네고 등록자가 내가 등록한 네고를 확인하는 경우
			List<NegotiationEntity> entities = negotiationRepository.findAllBySalesItem_ItemIdAndWriter(itemId, requestWriter)
					.orElseThrow( () -> new ApplicationException(ErrorCode.NEGOTIATION_NOT_FOUND));

			if (entities.stream().noneMatch(entity -> isValidPassword(password, entity))) {
				throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
			}

			return new PageImpl<>(entities.stream()
									.map(NegotiationResponse::fromEntity)
									.collect(Collectors.toList()),
								pageable,
								entities.size());
		}
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
		List<NegotiationEntity> rejectedProposals =  negotiationRepository.findAllBySalesItem_ItemId(itemId);
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

}
