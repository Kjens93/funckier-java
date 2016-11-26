package io.github.kjens93.funkier;

/**
 * Created by kjensen on 11/26/16.
 */
@FunctionalInterface
public interface ThrowingSupplier<T> {
    T get() throws Throwable;
}
