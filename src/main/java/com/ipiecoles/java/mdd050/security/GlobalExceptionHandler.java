package com.ipiecoles.java.mdd050.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
                        //Type d'exception que l'on souhaite gérer
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(EntityNotFoundException entityNotFoundException){
        //On peut mettre ce que l'on veut, écrire dans un fichier de log, envoyé un mail ... plein de possibilité
        return entityNotFoundException.getMessage();
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleEntityExistsException(EntityExistsException entityExistsException){
        //On peut mettre ce que l'on veut, écrire dans un fichier de log, envoyé un mail ... plein de possibilité
        return entityExistsException.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArdumentException(IllegalArgumentException e){
        return e.getMessage();
    }
}
