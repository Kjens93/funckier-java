package io.github.kjens93.funkier;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by kjensen on 11/25/16.
 */
@Deprecated
public interface Coerce {

    @Deprecated
    static <T, V extends Throwable> T coerce(ThrowingSupplier<T> supplier, Class<V> clazz) throws V, IllegalArgumentException {
        Constructor<V> constructor;
        try {
            constructor = clazz.getConstructor(Throwable.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format(
                    "Class %s does not declare a constructor accepting a single parameter of type %s.",
                    clazz.getName(), Throwable.class.getName()), e);
        }
        try {
            return supplier.get();
        } catch (Throwable t) {
            try {
                throw constructor.newInstance(t);
            }
            catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                RuntimeException exception = new RuntimeException(t);
                exception.addSuppressed(e);
                throw exception;
            }
        }
    }

    @Deprecated
    static <T> T coerce(ThrowingSupplier<T> supplier) throws RuntimeException {
        return coerce(supplier, RuntimeException.class);
    }

    @Deprecated
    static <V extends Throwable> void coerce(ThrowingRunnable runnable, Class<V> clazz) throws V, IllegalArgumentException {
        coerce(() -> {
            runnable.run();
            return true;
        }, clazz);
    }

    @Deprecated
    static void coerce(ThrowingRunnable callable) throws RuntimeException {
        coerce(callable, RuntimeException.class);
    }

}
