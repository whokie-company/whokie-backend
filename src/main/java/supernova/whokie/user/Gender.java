package supernova.whokie.user;

import com.fasterxml.jackson.annotation.JsonValue;

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
        throw new IllegalArgumentException("Invalid gender value: " + gender);
    }
}
