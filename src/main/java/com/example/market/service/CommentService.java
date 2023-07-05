package com.example.market.service;

import com.example.market.entity.CommentEntity;
import com.example.market.entity.SalesItemEntity;
import com.example.market.exception.ApplicationException;
import com.example.market.exception.ErrorCode;
import com.example.market.repository.CommentRepository;
import com.example.market.repository.SalesItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final SalesItemRepository salesItemRepository;
	private final CommentRepository commentRepository;
	public void create(Long itemId, String writer, String password, String contents){
		SalesItemEntity savedItem = salesItemRepository.findById(itemId).orElseThrow( () ->
			new ApplicationException(ErrorCode.SALES_ITEM_NOT_FOUND));

		commentRepository.save(CommentEntity.of(savedItem, writer, password, contents));
	}

}
