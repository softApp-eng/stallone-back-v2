package far.insp.sirhat.advice;


import far.insp.sirhat.exception.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class BadRequestExceptionHandler
 {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
   List<String> details = new ArrayList<>();
   for (ObjectError error : ex.getBindingResult().getAllErrors()) {
    details.add(error.getDefaultMessage());
   }
   return ResponseEntity.status(HttpStatus.BAD_REQUEST).
           body(
                   Message.builder()
                   .statusCode(HttpStatus.BAD_REQUEST.value())
                   .message("BAD_REQUEST")
                   .description(details)
                   .timestamp(new Date())
                   .build()
   );
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(AuthenticationException.class)
  @ResponseBody
  public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e) {
   log.error(e.getMessage());
   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
           body(
                   Message.builder()
                   .statusCode(HttpStatus.UNAUTHORIZED.value())
                   .message("UNAUTHORIZED")
                   .description(e.getMessage())
                   .timestamp(new Date())
                   .build()
           );
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseBody
  public ResponseEntity<Object> handle404Exception(AuthenticationException e) {
   log.error(e.getMessage());
   return ResponseEntity.status(HttpStatus.NOT_FOUND).
           body(
                   Message.builder()
                   .statusCode(HttpStatus.NOT_FOUND.value())
                   .message("NOT_FOUND")
                   .description(e.getMessage())
                   .timestamp(new Date())
                   .build()
           );
  }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> badRequest(HttpServletRequest req, Exception exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(Message.builder()
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .message("BAD REQUEST")
                        .description(exception.getMessage())
                        .timestamp(new Date())
                        .build());
    }
}
