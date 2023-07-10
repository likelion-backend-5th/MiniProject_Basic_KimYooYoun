package com.example.market.constants;

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
	SUCCESS_COMMENT_DELETE("댓글을 삭제했습니다."),

	SUCCESS_NEGOTIATION_CREATE("구매 제안이 등록되었습니다."),
	SUCCESS_NEGOTIATION_MODIFY("제안이 수정되었습니다."),
	SUCCESS_NEGOTIATION_DELETE("제안을 삭제했습니다."),
	SUCCESS_NEGOTIATION_STATUS("제안의 상태가 변경되었습니다."),
	SUCCESS_NEGOTIATION_APPROVED("구매가 확정되었습니다.")
	;

	private final String message;
}
