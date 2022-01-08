package com.usermanagement.boot.exception;

public class AddressNotFoundException extends RuntimeException {

	public AddressNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public AddressNotFoundException(String message) {
		super(message);
	}

	public AddressNotFoundException(Throwable cause) {
		super(cause);
	}

}
