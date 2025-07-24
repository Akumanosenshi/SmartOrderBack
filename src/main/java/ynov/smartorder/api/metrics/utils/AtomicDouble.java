package ynov.smartorder.api.metrics.utils;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicDouble extends Number {
    private final AtomicReference<Double> ref;

    public AtomicDouble(double initialValue) {
        this.ref = new AtomicReference<>(initialValue);
    }

    public void set(double value) {
        ref.set(value);
    }

    public double get() {
        return ref.get(); // retourne primitif
    }

    @Override
    public int intValue() {
        return ref.get().intValue(); // Double -> int
    }

    @Override
    public long longValue() {
        return ref.get().longValue(); // Double -> long
    }

    @Override
    public float floatValue() {
        return ref.get().floatValue(); // Double -> float
    }

    @Override
    public double doubleValue() {
        return ref.get(); // double primitif
    }
}
