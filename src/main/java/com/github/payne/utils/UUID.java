package com.github.payne.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class UUID {

    private static AtomicInteger counter = new AtomicInteger();

    public static int getUniqueId() {
        return counter.getAndIncrement();
    }
}
