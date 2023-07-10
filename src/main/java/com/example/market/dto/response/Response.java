package com.example.market.dto.response;

import com.example.market.constants.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response<T> {

	private T message;

	public static <T> Response<T> success(T message) {
		return new Response<>(message);
	}

	public static <T> Response<String> success(ResponseMessage type) {
		return new Response<>(type.getMessage());
	}

}
