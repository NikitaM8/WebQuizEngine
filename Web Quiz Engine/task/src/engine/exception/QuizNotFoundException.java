package engine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class QuizNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public QuizNotFoundException(String message){
        super(message);
    }
}
