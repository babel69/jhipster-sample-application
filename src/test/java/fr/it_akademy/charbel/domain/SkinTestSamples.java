package fr.it_akademy.charbel.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SkinTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Skin getSkinSample1() {
        return new Skin().id(1L).color("color1");
    }

    public static Skin getSkinSample2() {
        return new Skin().id(2L).color("color2");
    }

    public static Skin getSkinRandomSampleGenerator() {
        return new Skin().id(longCount.incrementAndGet()).color(UUID.randomUUID().toString());
    }
}
