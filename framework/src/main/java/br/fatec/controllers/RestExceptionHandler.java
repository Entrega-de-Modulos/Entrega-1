package br.fatec.controllers;

import br.fatec.dtos.ErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;

@ControllerAdvice
public class RestExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDto handleAllExceptions(Exception ex, HttpServletRequest request) {
        logger.error("Erro não tratado: ", ex);
        return new ErrorDto(
                Instant.now(),
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        );
    }
}
