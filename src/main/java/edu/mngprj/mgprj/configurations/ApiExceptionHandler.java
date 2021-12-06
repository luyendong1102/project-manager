package edu.mngprj.mgprj.configurations;

import edu.mngprj.mgprj.entities.ResponseTemplate;
import edu.mngprj.mgprj.exceptions.NotFoundException;
import edu.mngprj.mgprj.exceptions.NotValidUserException;
import edu.mngprj.mgprj.exceptions.PasswordNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(PasswordNotMatchException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseTemplate> passwordnotMatch(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseTemplate(400, "Password not match", null)
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ResponseEntity<ResponseTemplate> forbidden(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ResponseTemplate(HttpStatus.FORBIDDEN.value(), "Forbiddened", null)
        );
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ResponseTemplate> unauthorized(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ResponseTemplate(HttpStatus.UNAUTHORIZED.value(), "Unauthozired", null)
        );
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseTemplate> uniqueconstrain(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseTemplate(HttpStatus.BAD_REQUEST.value(), "Username must be unique", null)
        );
    }

    @ExceptionHandler(NotValidUserException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseTemplate> notvaliduser(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseTemplate(HttpStatus.BAD_REQUEST.value(), "Not valid user", null)
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseTemplate> notfound(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseTemplate(HttpStatus.BAD_REQUEST.value(), "Cannot find your request", null)
        );
    }

}
