package com.example.market.controller;

import com.example.market.dto.CommentDto;
import com.example.market.dto.request.CommentDeleteRequest;
import com.example.market.dto.request.CommentReplyRequest;
import com.example.market.dto.request.CommentRequest;
import com.example.market.dto.response.Response;
import com.example.market.dto.response.ResponseMessage;
import com.example.market.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items/{itemId}/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
	private final CommentService commentService;

	@PostMapping
	public Response<String> create(@PathVariable Long itemId, @RequestBody CommentRequest request){
		commentService.create(itemId, request.getWriter(), request.getPassword(), request.getContent());
		return Response.success(ResponseMessage.SUCCESS_COMMENT_CREATE);
	}
	@GetMapping
	public Response<Page<CommentDto>> getCommentsByItem(@PathVariable Long itemId){
		return Response.success(commentService.getAllCommentByItem(itemId));
	}

	@PutMapping("/{commentId}")
	public Response<String> modify(@PathVariable Long itemId,
								@PathVariable Long commentId,
								@RequestBody CommentRequest request){
		commentService.modify(itemId, commentId, request.getWriter(), request.getPassword(), request.getContent());
		return Response.success(ResponseMessage.SUCCESS_COMMENT_MODIFY);
	}

	@PutMapping("/{commentId}/reply")
	public Response<String> reply(@PathVariable Long itemId,
								@PathVariable Long commentId,
								@RequestBody CommentReplyRequest request){
		commentService.reply(itemId, commentId, request.getWriter(), request.getPassword(), request.getReply());
		return Response.success(ResponseMessage.SUCCESS_COMMENT_REPLY_CREATE);
	}

	@DeleteMapping("/{commentId}")
	public Response<String> delete(@PathVariable Long itemId,
									@PathVariable Long commentId,
									@RequestBody CommentDeleteRequest request){
		commentService.delete(itemId, commentId, request.getWriter(), request.getPassword());
		return Response.success(ResponseMessage.SUCCESS_COMMENT_DELETE);
	}

}
