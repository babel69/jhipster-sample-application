package fr.it_akademy.charbel.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CharacterTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Character getCharacterSample1() {
        return new Character()
            .id(1L)
            .name("name1")
            .surname("surname1")
            .countryName("countryName1")
            .jobName("jobName1")
            .powerName("powerName1")
            .age(1);
    }

    public static Character getCharacterSample2() {
        return new Character()
            .id(2L)
            .name("name2")
            .surname("surname2")
            .countryName("countryName2")
            .jobName("jobName2")
            .powerName("powerName2")
            .age(2);
    }

    public static Character getCharacterRandomSampleGenerator() {
        return new Character()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .surname(UUID.randomUUID().toString())
            .countryName(UUID.randomUUID().toString())
            .jobName(UUID.randomUUID().toString())
            .powerName(UUID.randomUUID().toString())
            .age(intCount.incrementAndGet());
    }
}
