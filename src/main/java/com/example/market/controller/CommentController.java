package com.example.market.controller;

import com.example.market.dto.request.CommentDeleteRequest;
import com.example.market.dto.request.CommentRequest;
import com.example.market.dto.response.Response;
import com.example.market.dto.response.ResponseMessage;
import com.example.market.service.CommentService;
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
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
	private final CommentService commentService;

	@PostMapping("/{itemId}/comments")
	public Response<String> create(@PathVariable Long itemId, @RequestBody CommentRequest request){
		commentService.create(itemId, request.getWriter(), request.getPassword(), request.getContent());
		return Response.success(ResponseMessage.SUCCESS_COMMENT_CREATE);
	}

	@PutMapping("/{itemId}/comments/{commentId}")
	public Response<String> modify(@PathVariable Long itemId,
									@PathVariable Long commentId,
									@RequestBody CommentRequest request){
		commentService.modify(itemId, commentId, request.getWriter(), request.getPassword(), request.getContent());
		return Response.success(ResponseMessage.SUCCESS_COMMENT_MODIFY);
	}

	@DeleteMapping("/{itemId}/comments/{commentId}")
	public Response<String> delete(@PathVariable Long itemId,
									@PathVariable Long commentId,
									@RequestBody CommentDeleteRequest request){
		commentService.delete(itemId, commentId, request.getWriter(), request.getPassword());
		return Response.success(ResponseMessage.SUCCESS_COMMENT_DELETE);
	}

}
