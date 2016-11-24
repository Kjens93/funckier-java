package io.github.kjens93.funkier;

/**
 * Created by kjensen on 11/24/16.
 */
@FunctionalInterface
public interface ThrowingRunnable {

    void run() throws Exception;

    static void sneak(ThrowingRunnable runnable) throws RuntimeException {
        try {
            runnable.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
