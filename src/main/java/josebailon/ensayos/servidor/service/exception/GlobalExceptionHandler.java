/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */

package josebailon.ensayos.servidor.service.exception;

import com.fasterxml.jackson.annotation.JsonView;
import josebailon.ensayos.servidor.model.vistas.Vista;
import org.hibernate.NonUniqueObjectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Jose Javier Bailon Ortiz
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<String> handleDuplicatedEmailException(final DuplicatedEmailException ex){
        return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(final ResponseStatusException ex){
        return new ResponseEntity<String>(ex.getMessage(),ex.getStatusCode());
    }

    @ExceptionHandler(VersionIncorrectaException.class)
     @RequestMapping(produces = "application/json")
    @JsonView(Vista.Esencial.class)
    public ResponseEntity<Object> handleVersionIncorrectaException(final VersionIncorrectaException ex){
        return new ResponseEntity<Object>(ex.getValor(),HttpStatus.CONFLICT);

    }
    @ExceptionHandler(NonUniqueObjectException.class)
     @RequestMapping(produces = "application/json")
    public ResponseEntity<Object> handleNonUniqueObjectException(final NonUniqueObjectException ex){
        return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.CONFLICT);

    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @RequestMapping(produces = "application/json")
    @JsonView(Vista.Esencial.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(final HttpMessageNotReadableException ex){
        return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @RequestMapping(produces = "application/json")
    public ResponseEntity<Object> handleMissingServletRequestPartException(final MissingServletRequestPartException ex){
        return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }        
}//end GlobalExceptionHandler
