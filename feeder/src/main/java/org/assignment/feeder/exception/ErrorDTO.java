package org.assignment.feeder.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private int status;
    private String message;
    
	private ErrorDTO(ErrorDTOBuilder builder) {
		this.timestamp = builder.timestamp;
		this.status = builder.status;
		this.message = builder.message;
	}
    
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String error) {
		this.message = error;
	}
	
	public static class ErrorDTOBuilder {
	    private LocalDateTime timestamp;
	    private int status;
	    private String message;
	    
		public ErrorDTOBuilder withTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public ErrorDTOBuilder withStatus(int status) {
			this.status = status;
			return this;
		}

		public ErrorDTOBuilder withMessage(String message) {
			this.message = message;
			return this;
		}

		public ErrorDTO build() {
			return new ErrorDTO(this);
		}
	}
}
