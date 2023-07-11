package com.example.market.dto.response;

import com.example.market.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponse {
	private Long id;
	private String content;
	private String reply;

	public static CommentResponse fromEntity(CommentEntity entity){
		return new CommentResponse(
			entity.getId(),
			entity.getContent(),
			entity.getReply()
		);
	}
}
