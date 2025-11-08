package tech.sergeyev.linkshort.service;

import lombok.experimental.UtilityClass;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

@UtilityClass
public class TokenGenerator {
    private final RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .withinRange('0', 'z')
            .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
            .get();

    public static String generateToken(int length) {
        return generator.generate(length);
    }
}
