package com.it;

public class UserNameNotFoundException extends RuntimeException {

	 public UserNameNotFoundException(String name) {
	        super("User with id " + name + " not found");
	    }

}
