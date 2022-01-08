package com.usermanagement.boot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class UserRestExceptionHandler {
	
	/*
	 * User exception handler method  
	 * 
	 * 
	 * specific error handler
	 * */  
	
	@ExceptionHandler
	public ResponseEntity<UserErrorResponse> handleException(UserNotFoundException userNotFoundException){
		UserErrorResponse userErrorResponse =  new UserErrorResponse(); 
		userErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		userErrorResponse.setMessage(userNotFoundException.getMessage());
		userErrorResponse.setTimeStamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(userErrorResponse,HttpStatus.NOT_FOUND);
		
	}
	
	//Generic exception handler
	@ExceptionHandler   
	public ResponseEntity<UserErrorResponse> handleException(Exception userNotFoundException){
		UserErrorResponse userErrorResponse =  new UserErrorResponse(); 
		userErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		userErrorResponse.setMessage(userNotFoundException.getMessage());
		userErrorResponse.setTimeStamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(userErrorResponse,HttpStatus.BAD_REQUEST);
		
	}
	
	 @ExceptionHandler(MaxUploadSizeExceededException.class)
	  public ResponseEntity<UserErrorResponse> handleMaxSizeException(MaxUploadSizeExceededException exc) {
		 
			UserErrorResponse userErrorResponse =  new UserErrorResponse(); 
			userErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			userErrorResponse.setMessage("File too large!");
			userErrorResponse.setTimeStamp(System.currentTimeMillis());
			
			return new ResponseEntity<>(userErrorResponse,HttpStatus.BAD_REQUEST);
	  }
	

}
