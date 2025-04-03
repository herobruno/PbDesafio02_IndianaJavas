package indiana.javas.msauthorization.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ErrorMessage {
    private final String statusMessage;
    private final int httpStatus;
    private String message;
    private Map<String, String> errors;


    private ErrorMessage(HttpStatus httpStatus) {
        this.httpStatus = httpStatus.value();
        this.statusMessage = httpStatus.getReasonPhrase();
    }

    public ErrorMessage(HttpStatus httpStatus, String message) {
        this(httpStatus);
        this.message = message;
    }

    public ErrorMessage(HttpStatus httpStatus, BindingResult bindingResult) {
        this(httpStatus);
        initializeErrors(bindingResult);

    }

    private void initializeErrors(BindingResult bindingResult) {
        errors = new HashMap<String, String>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

}
