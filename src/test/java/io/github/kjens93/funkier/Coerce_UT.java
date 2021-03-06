package io.github.kjens93.funkier;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.Callable;

import static io.github.kjens93.funkier.Coerce.coerce;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by kjensen on 11/25/16.
 */
public class Coerce_UT {

    private static final IllegalStateException unchecked = new IllegalStateException();
    private static final IOException checked = new IOException();
    private static final ThrowingRunnable throwingRunnable = () -> { throw checked; };
    private static final Callable<String> callable = () -> { throw checked; };
    private static final Runnable runnable = () -> { throw unchecked; };

    @Test
    public void test_coerce_functional_ThrowingRunnable() {
        assertThatThrownBy(() -> coerce(this::throwingRunnable))
                .isInstanceOf(RuntimeException.class)
                .hasCause(checked);

        assertThatThrownBy(() -> coerce(this::throwingRunnable, IllegalStateException.class))
                .isInstanceOf(IllegalStateException.class)
                .hasCause(checked);

        assertThatThrownBy(() -> coerce(this::throwingRunnable, InvalidException1.class))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> coerce(this::throwingRunnable, InvalidException2.class))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void test_coerce_ThrowingRunnable() {
        assertThatThrownBy(() -> coerce(throwingRunnable))
                .isInstanceOf(RuntimeException.class)
                .hasCause(checked);

        assertThatThrownBy(() -> coerce(throwingRunnable, IllegalStateException.class))
                .isInstanceOf(IllegalStateException.class)
                .hasCause(checked);

        assertThatThrownBy(() -> coerce(throwingRunnable, InvalidException1.class))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> coerce(throwingRunnable, InvalidException2.class))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void test_coerce_functional_Runnable() {
        assertThatThrownBy(() -> coerce(this::runnable))
                .isInstanceOf(RuntimeException.class)
                .hasCause(unchecked);

        assertThatThrownBy(() -> coerce(this::runnable, IllegalStateException.class))
                .isInstanceOf(IllegalStateException.class)
                .hasCause(unchecked);

        assertThatThrownBy(() -> coerce(this::runnable, InvalidException1.class))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> coerce(this::runnable, InvalidException2.class))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void test_coerce_Runnable() {
        assertThatThrownBy(() -> coerce(runnable::run))
                .isInstanceOf(RuntimeException.class)
                .hasCause(unchecked);

        assertThatThrownBy(() -> coerce(runnable::run, IllegalStateException.class))
                .isInstanceOf(IllegalStateException.class)
                .hasCause(unchecked);

        assertThatThrownBy(() -> coerce(runnable::run, InvalidException1.class))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> coerce(runnable::run, InvalidException2.class))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void test_coerce_functional_Callable() {
        assertThatThrownBy(() -> coerce(this::callable))
                .isInstanceOf(RuntimeException.class)
                .hasCause(checked);

        assertThatThrownBy(() -> coerce(this::callable, IllegalStateException.class))
                .isInstanceOf(IllegalStateException.class)
                .hasCause(checked);

        assertThatThrownBy(() -> coerce(this::callable, InvalidException1.class))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> coerce(this::callable, InvalidException2.class))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void test_coerce_Callable() {
        assertThatThrownBy(() -> coerce(callable::call))
                .isInstanceOf(RuntimeException.class)
                .hasCause(checked);

        assertThatThrownBy(() -> coerce(callable::call, IllegalStateException.class))
                .isInstanceOf(IllegalStateException.class)
                .hasCause(checked);

        assertThatThrownBy(() -> coerce(callable::call, InvalidException1.class))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> coerce(callable::call, InvalidException2.class))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private int callable() throws IOException {
        throw checked;
    }

    private void runnable() {
        throw unchecked;
    }

    private void throwingRunnable() throws IOException {
        throw checked;
    }

    private static class InvalidException1 extends Exception {
        public InvalidException1() {
            super();
        }
    }

    private static class InvalidException2 extends Exception {
        public InvalidException2(IOException exception) {
            super(exception);
        }
    }

}
