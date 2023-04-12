package me.frankmms.divideconta.infrastructure.restapi;

import me.frankmms.divideconta.application.model.ErrorDTO;
import me.frankmms.divideconta.domain.DomainException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;

@ControllerAdvice
public class ApiRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ValidationException.class })
    protected ResponseEntity<Object> handle(ValidationException ex, WebRequest request) {
        var errorDTO = new ErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage());

        return handleExceptionInternal(ex, errorDTO, new HttpHeaders(), errorDTO.getStatus(), request);
    }

    @ExceptionHandler({ DomainException.class })
    protected ResponseEntity<Object> handle(DomainException ex, WebRequest request) {
        var errorDTO = new ErrorDTO(HttpStatus.CONFLICT, ex.getMessage());

        return handleExceptionInternal(ex, errorDTO, new HttpHeaders(), errorDTO.getStatus(), request);
    }

}
