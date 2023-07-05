package com.example.market.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
	SUCCESS_ITEM_CREATE("등록이 완료되었습니다."),
	SUCCESS_ITEM_MODIFY("물품이 수정되었습니다."),
	SUCCESS_ITEM_DELETE("물품을 삭제했습니다."),
	SUCCESS_ITEM_IMAGE("이미지가 등록되었습니다."),
	SUCCESS_COMMENT_CREATE("댓글이 등록되었습니다."),
	SUCCESS_COMMENT_MODIFY("댓글이 수정되었습니다."),
	SUCCESS_COMMENT_REPLY_CREATE("댓글에 답변이 추가되었습니다."),
	SUCCESS_COMMENT_DELETE("댓글을 삭제했습니다.")

	;

	private final String message;
}
