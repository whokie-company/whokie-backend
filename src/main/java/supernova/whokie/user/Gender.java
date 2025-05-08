package supernova.whokie.user;

import com.fasterxml.jackson.annotation.JsonValue;
import supernova.whokie.global.exception.InvalidGenderException;

public enum Gender {
    F("female"),
    M("male");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static Gender fromString(String gender) {
        for (Gender g : Gender.values()) {
            if (g.value.equalsIgnoreCase(gender)) {
                return g;
            }
        }
        throw new InvalidGenderException("Invalid gender value: " + gender);
    }
}
