package com.example.market.service;

import com.example.market.entity.CommentEntity;
import com.example.market.entity.SalesItemEntity;
import com.example.market.exception.ApplicationException;
import com.example.market.exception.ErrorCode;
import com.example.market.repository.CommentRepository;
import com.example.market.repository.SalesItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final SalesItemRepository salesItemRepository;
	private final CommentRepository commentRepository;
	@Transactional
	public void create(Long itemId, String writer, String password, String contents){
		SalesItemEntity savedItem = salesItemRepository.findById(itemId).orElseThrow( () ->
			new ApplicationException(ErrorCode.SALES_ITEM_NOT_FOUND));

		commentRepository.save(CommentEntity.of(savedItem, writer, password, contents));
	}
	@Transactional
	public void modify(Long itemId, Long commentId, String writer, String inputPassword, String contents){
		SalesItemEntity savedItem = salesItemRepository.findById(itemId).orElseThrow( () ->
			new ApplicationException(ErrorCode.SALES_ITEM_NOT_FOUND));

		CommentEntity savedComment = commentRepository.findById(commentId).orElseThrow( () ->
			new ApplicationException(ErrorCode.COMMENT_NOT_FOUND));

		if(!isValidPassword(inputPassword, savedComment))
			throw new ApplicationException(ErrorCode.INVALID_PASSWORD);

		savedComment.updateComment(writer, inputPassword, contents);
		commentRepository.saveAndFlush(savedComment);
	}
	@Transactional
	public void reply(Long itemId, Long commentId, String writer, String inputPassword, String reply){
		SalesItemEntity savedItem = salesItemRepository.findById(itemId).orElseThrow( () ->
			new ApplicationException(ErrorCode.SALES_ITEM_NOT_FOUND));

		CommentEntity savedComment = commentRepository.findById(commentId).orElseThrow( () ->
			new ApplicationException(ErrorCode.COMMENT_NOT_FOUND));

		if(!isValidPassword(inputPassword, savedComment))
			throw new ApplicationException(ErrorCode.INVALID_PASSWORD);

		savedComment.addReply(reply);
		commentRepository.saveAndFlush(savedComment);
	}
	private boolean isValidPassword(String inputPassword, CommentEntity savedItem){
		return inputPassword.equals(savedItem.getPassword());
	}
	@Transactional
	public void delete(Long itemId, Long commentId, String writer, String inputPassword){
		SalesItemEntity savedItem = salesItemRepository.findById(itemId).orElseThrow( () ->
			new ApplicationException(ErrorCode.SALES_ITEM_NOT_FOUND));

		CommentEntity savedComment = commentRepository.findById(commentId).orElseThrow( () ->
			new ApplicationException(ErrorCode.COMMENT_NOT_FOUND));

		if(!isValidPassword(inputPassword, savedComment))
			throw new ApplicationException(ErrorCode.INVALID_PASSWORD);

		commentRepository.delete(savedComment);
	}

}
