package eu.senla.card.util;

import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class SecureRandom16DigitNumber {

    @NotNull
    public static Long generateRandomNumber() {
        final SecureRandom secureRandom = new SecureRandom();
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            stringBuilder.append(secureRandom.nextInt(10));
        }
        return Long.parseLong(stringBuilder.toString());
    }
}
