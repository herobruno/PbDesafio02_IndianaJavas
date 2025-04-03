package indiana.javas.msproducts.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
@Generated
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RestErrorMessage> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        RestErrorMessage response = new RestErrorMessage(status, request, e.getMessage());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<RestErrorMessage> handleDatabaseException(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        RestErrorMessage response = new RestErrorMessage(status, request, e.getMessage());
        log.error("Api Error - ", e);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler({DuplicateCategoryNameException.class})
    public ResponseEntity<RestErrorMessage> duplicationViolationException(DuplicateCategoryNameException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        RestErrorMessage response = new RestErrorMessage(status, request, e.getMessage());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<RestErrorMessage> handleValidationException(ValidationException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        RestErrorMessage response = new RestErrorMessage(status, request, e.getMessage());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request,
                                                                        BindingResult result) {

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        RestErrorMessage response = new RestErrorMessage(status, request, "invalid(s) field(s)", result);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorMessage> handleGlobalException(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        RestErrorMessage response = new RestErrorMessage(status, request, e.getMessage());
        return ResponseEntity.status(status).body(response);
    }
}
