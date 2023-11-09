package fr.it_akademy.charbel.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PowerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Power getPowerSample1() {
        return new Power().id(1L).name("name1");
    }

    public static Power getPowerSample2() {
        return new Power().id(2L).name("name2");
    }

    public static Power getPowerRandomSampleGenerator() {
        return new Power().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
