package indiana.javas.msproducts.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Generated
public class RestErrorMessage {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant timestamp;
    private int status;
    private String statusMessage;
    private String errorMessage;
    private String path;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    public RestErrorMessage(HttpStatus status, HttpServletRequest request, String error) {
        this.timestamp = Instant.now();
        this.status = status.value();
        this.statusMessage =  status.getReasonPhrase();
        this.path = request.getRequestURI();
        this.errorMessage = error;
    }

    public RestErrorMessage(HttpStatus status, HttpServletRequest request, String error, BindingResult result) {
        this.timestamp = Instant.now();
        this.status = status.value();
        this.statusMessage =  status.getReasonPhrase();
        this.path = request.getRequestURI();
        this.errorMessage = error;
        extractErrors(result);
    }

    private void extractErrors(BindingResult bindingResult) {
        this.errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
