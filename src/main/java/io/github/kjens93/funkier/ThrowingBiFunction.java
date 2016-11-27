package io.github.kjens93.funkier;

/**
 * Created by kjensen on 11/26/16.
 */
@FunctionalInterface
public interface ThrowingBiFunction<T, V, R, E extends Exception> {
    R apply(T obj1, V obj2) throws E;
}
