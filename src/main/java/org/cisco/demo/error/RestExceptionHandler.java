package org.cisco.demo.error;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cisco.demo.controllers.ClientController;
import org.cisco.demo.exceptions.ObjectNotFoundException;
import org.cisco.demo.service.ClientServiceImpl;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(assignableTypes = ClientController.class)
@RequestMapping(produces = "application/json")
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	
	private static final Logger logger = LogManager.getLogger(ClientServiceImpl.class);

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error("ClientController : handleHttpMessageNotReadable Exception");
		String errorMessage = "Malformed JSON request";
		String path = getPath(request.getDescription(false));
		ApiError apierror = new ApiError(HttpStatus.BAD_REQUEST, errorMessage, path);
		return buildResponseEntity(apierror);
	}
	
	@ExceptionHandler(ObjectNotFoundException.class)
	protected ResponseEntity<Object> handleObjectNotFoundException( ObjectNotFoundException ex) {
		logger.error("ClientController : handleObjectNotFoundException");
		ApiError apierror = new ApiError(HttpStatus.NOT_FOUND, ex.getErrorMessage());
		return buildResponseEntity(apierror);
	}
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleException( Exception ex) {
		logger.error("ClientController : handleException");
		String errorMessage = "Unhandled Exception occurred";
		ApiError apierror = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
		return buildResponseEntity(apierror);
	} 
	
	private String getPath(String description) {
		return description;
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<Object>(apiError, apiError.getStatus());
	}
}
