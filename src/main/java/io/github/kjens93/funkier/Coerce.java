package io.github.kjens93.funkier;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

/**
 * Created by kjensen on 11/25/16.
 */
public interface Coerce {

    static <T, V extends Throwable> T coerce(Callable<T> callable, Class<V> clazz) throws V, IllegalArgumentException {
        Constructor<V> constructor;
        try {
            constructor = clazz.getConstructor(Throwable.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format(
                    "Class %s does not declare a constructor accepting a single parameter of type %s.",
                    clazz.getName(), Throwable.class.getName()), e);
        }
        try {
            return callable.call();
        } catch (Exception e) {
            try {
                throw constructor.newInstance(e);
            }
            catch (IllegalAccessException | InstantiationException | InvocationTargetException e1) {
                RuntimeException exception = new RuntimeException(e);
                exception.addSuppressed(e1);
                throw exception;
            }
        }
    }

    static <T> T coerce(Callable<T> callable) throws RuntimeException {
        return coerce(callable, RuntimeException.class);
    }

    static <V extends Throwable> void coerce(ThrowingRunnable runnable, Class<V> clazz) throws V, IllegalArgumentException {
        coerce(()->{
            runnable.run();
            return true;
        }, clazz);
    }

    static void coerce(ThrowingRunnable runnable) throws RuntimeException {
        coerce(runnable, RuntimeException.class);
    }

}
