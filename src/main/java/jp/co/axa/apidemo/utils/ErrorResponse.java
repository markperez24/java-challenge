package jp.co.axa.apidemo.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.Setter;

public class ErrorResponse {
	@Getter
	@Setter
	private String timestamp;

	@Getter
	@Setter
	private int status;

	@Getter
	@Setter
	private String error;

	@Getter
	@Setter
	private String message;

	@Getter
	@Setter
	private String path;

	public ErrorResponse(String timestamp, int status, String error, String message, String path) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}	
	public ErrorResponse(String timestamp, int status, String error, String message, HttpServletRequest request) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = request.getRequestURI();
	}	
	public ErrorResponse(int status, String error, String message, String path) {
		this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}	
	public ErrorResponse(int status, String error, String message, HttpServletRequest request) {
		this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = request.getRequestURI();
	}
	public ErrorResponse(HttpStatus httpStatus, String message, HttpServletRequest request) {
		this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		this.status = httpStatus.value();
		this.error = httpStatus.getReasonPhrase();
		this.message = message;
		this.path = request.getRequestURI();	
	}	
	public ErrorResponse(int statusCode, String message, HttpServletRequest request) {
		try 
		{
			HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
			this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			this.status = httpStatus.value();
			this.error = httpStatus.getReasonPhrase();
			this.message = message;
			this.path = request.getRequestURI();
		} 
		catch(IllegalArgumentException ex) {
			HttpStatus httpStatus = HttpStatus.valueOf(500);
			this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			this.status = httpStatus.value();
			this.error = httpStatus.getReasonPhrase();
			this.message = message;
			this.path = request.getRequestURI();
		}		
	}
}
