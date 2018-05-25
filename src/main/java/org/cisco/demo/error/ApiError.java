package org.cisco.demo.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError {

	private HttpStatus status;
	private String path;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private String timestamp;
	private String message;
	private String debugMessage;

	private ApiError() {
		timestamp = LocalDateTime.now().toString();
	}

	public ApiError(HttpStatus status) {
		this();
		this.status = status;
	}
	
	public ApiError(HttpStatus status, String message) {
		this();
		this.status = status;
		this.message = message;
	}


	public ApiError(HttpStatus status, String message, String path) {
		this();
		this.status = status;
		this.message = message;
		this.setPath(path);
	}

	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the debugMessage
	 */
	public String getDebugMessage() {
		return debugMessage;
	}

	/**
	 * @param debugMessage the debugMessage to set
	 */
	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
