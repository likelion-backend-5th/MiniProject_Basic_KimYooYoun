package com.example.market.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Response<T> {
	private String message;
	public Response(ResponseMessage message){
		this.message = message.getMessage();
	}
}
