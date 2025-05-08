package supernova.whokie.global.exception;

import org.springframework.http.HttpStatus;

public class InvalidGenderException extends CustomException {

    private static final String DEFAULT_TITLE = "Invalid Gender Value";

    public InvalidGenderException(String message) {
        super(message, HttpStatus.BAD_REQUEST, DEFAULT_TITLE);
    }
}
