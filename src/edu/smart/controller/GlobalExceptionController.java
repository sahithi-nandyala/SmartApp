package edu.smart.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.smart.constants.SmartConstants;

@ControllerAdvice
public class GlobalExceptionController{
	
	@ExceptionHandler(value = Exception.class)
	public String defaultErrorHandler(Exception e){
	   
	    return SmartConstants.DEFAULT_ERROR_VIEW;
	}
	
	

    
}
