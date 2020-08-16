package org.assignment.feeder.exception;

import java.time.LocalDateTime;

import org.assignment.feeder.exception.ErrorDTO.ErrorDTOBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GenericExceptionHandler {
	
	private static Logger LOGGER = LoggerFactory.getLogger(GenericExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> exception(Exception ex) {
    	
    	LOGGER.error(ex.getMessage(), ex);
    	
    	HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    	
    	if(ex instanceof ArticleNotFoundException)
        	status = HttpStatus.BAD_REQUEST;
    	
    	ErrorDTOBuilder builder = new ErrorDTOBuilder()
    			.withTimestamp(LocalDateTime.now())
    			.withStatus(status.value())
    			.withMessage(ex.getMessage());
    	
    	return new ResponseEntity<>(builder.build(), status);
    }
}
