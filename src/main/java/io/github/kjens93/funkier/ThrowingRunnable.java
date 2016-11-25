package io.github.kjens93.funkier;

/**
 * Created by kjensen on 11/24/16.
 */
@FunctionalInterface
public interface ThrowingRunnable {

    void run() throws Exception;

}
