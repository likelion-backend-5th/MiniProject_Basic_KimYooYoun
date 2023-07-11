package com.example.market.service;

import com.example.market.dto.response.CommentResponse;
import com.example.market.entity.CommentEntity;
import com.example.market.entity.SalesItemEntity;
import com.example.market.exception.ApplicationException;
import com.example.market.exception.ErrorCode;
import com.example.market.repository.CommentRepository;
import com.example.market.repository.SalesItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.example.market.util.ServiceUtils.isValidPassword;

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
	public Page<CommentResponse> getAllCommentByItem(Long itemId){
		Pageable pageable = PageRequest.of(0, 10);
		return commentRepository.findAllBySalesItem_ItemId(itemId, pageable).map(CommentResponse::fromEntity);
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

	@Transactional
	public void delete(Long itemId, String writer, String password){

		List<CommentEntity> savedComments = commentRepository.findAllBySalesItem_ItemId(itemId)
			.orElseThrow( () -> new ApplicationException(ErrorCode.COMMENT_NOT_FOUND));

		if (savedComments.stream().noneMatch(entity -> isValidPassword(password, entity))) {
			throw new ApplicationException(ErrorCode.INVALID_PASSWORD);
		}

		commentRepository.deleteAll(savedComments);
	}

}
