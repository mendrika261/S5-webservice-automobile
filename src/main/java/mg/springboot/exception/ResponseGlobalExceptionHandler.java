package mg.springboot.exception;

import mg.springboot.security.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class ResponseGlobalExceptionHandler
    extends ResponseEntityExceptionHandler {
  @Override
  protected ResponseEntity<Object>
  handleNoResourceFoundException(NoResourceFoundException ex,
                                 HttpHeaders headers, HttpStatusCode status,
                                 WebRequest request) {
    return new ResponseEntity<>(
        Response.send(HttpStatus.NOT_FOUND, "error",
                      "La ressource demandée n'existe pas: " +
                          ex.getResourcePath()),
        HttpStatus.NOT_FOUND);
  }
}
