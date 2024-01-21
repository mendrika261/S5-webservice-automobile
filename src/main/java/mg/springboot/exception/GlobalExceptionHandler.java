package mg.springboot.exception;

import java.util.HashMap;
import java.util.Map;
import mg.springboot.security.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Response<?>
  handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors())
      errors.put(error.getField(), error.getDefaultMessage());
    return Response.send(HttpStatus.BAD_REQUEST, "error",
                         errors.values().iterator().next(), errors);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ValidationException.class)
  public Response<?> handleValidationExceptions(ValidationException ex) {
    return Response.send(HttpStatus.BAD_REQUEST, "error", ex.getMessage());
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public Response<?> handleNotFoundExceptions(NotFoundException ex) {
    return Response.send(HttpStatus.NOT_FOUND, "error", ex.getMessage());
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(AccessDeniedException.class)
  public Response<?> handleAccessDeniedExceptions(AccessDeniedException ex) {
    return Response.send(HttpStatus.FORBIDDEN, "error", ex.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(DataIntegrityViolationException.class)
  public Response<?>
  handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    return Response.send(HttpStatus.BAD_REQUEST, "error",
                         "Cet objet existe déjà");
  }
}
