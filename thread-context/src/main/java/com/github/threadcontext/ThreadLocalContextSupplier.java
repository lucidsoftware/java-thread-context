package com.github.threadcontext;

import com.github.threadcontext.control.TryFinallyContext;
import java.util.function.Supplier;

public class ThreadLocalContextSupplier<T> implements Supplier<Context> {

    private final ThreadLocal<T> threadLocal;

    public ThreadLocalContextSupplier(ThreadLocal<T> threadLocal) {
        this.threadLocal = threadLocal;
    }

    public Context get() {
        T value = threadLocal.get();
        return new TryFinallyContext(() -> {
            T oldValue = threadLocal.get();
            threadLocal.set(value);
            return () -> threadLocal.set(oldValue);
        });
    }

}
