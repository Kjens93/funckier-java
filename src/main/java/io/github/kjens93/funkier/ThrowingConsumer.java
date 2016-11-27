package io.github.kjens93.funkier;

/**
 * Created by kjensen on 11/26/16.
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {
    void accept(T obj) throws E;
}
