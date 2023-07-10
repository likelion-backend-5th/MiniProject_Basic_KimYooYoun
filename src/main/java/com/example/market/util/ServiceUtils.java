package com.example.market.util;

import com.example.market.entity.PasswordCheckable;

public class ServiceUtils {
	public static boolean isValidPassword(String inputPassword, PasswordCheckable entity){
		return inputPassword.equals(entity.getPassword());
	}
}
