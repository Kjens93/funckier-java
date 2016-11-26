package io.github.kjens93.funkier;

/**
 * Created by kjensen on 11/26/16.
 */
@FunctionalInterface
public interface ThrowingFunction<T,R> {
    R apply(T obj) throws Exception;
}
