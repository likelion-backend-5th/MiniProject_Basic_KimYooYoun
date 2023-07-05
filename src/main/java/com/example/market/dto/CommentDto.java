package com.example.market.dto;

import com.example.market.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class CommentDto {
	private Long id;
	private String content;
	private String reply;

	public static CommentDto fromEntity(CommentEntity entity){
		return new CommentDto(
			entity.getId(),
			entity.getContent(),
			entity.getReply()
		);
	}
}
