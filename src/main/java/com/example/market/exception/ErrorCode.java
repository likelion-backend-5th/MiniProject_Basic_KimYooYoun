package com.example.market.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	SALES_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "SalesItem not founded"),
	COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Comment not founded"),
	NEGOTIATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Negotiation not founded"),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Password is invalid"),
	INVALID_STATUS(HttpStatus.BAD_REQUEST, "Status is invalid")
	;
	private final HttpStatus status;
	private final String message;
}
