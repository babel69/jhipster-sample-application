package fr.it_akademy.charbel.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class JobTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Job getJobSample1() {
        return new Job().id(1L).jobName("jobName1");
    }

    public static Job getJobSample2() {
        return new Job().id(2L).jobName("jobName2");
    }

    public static Job getJobRandomSampleGenerator() {
        return new Job().id(longCount.incrementAndGet()).jobName(UUID.randomUUID().toString());
    }
}
