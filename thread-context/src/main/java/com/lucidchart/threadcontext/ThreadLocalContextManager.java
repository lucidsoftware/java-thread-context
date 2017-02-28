package com.lucidchart.threadcontext;

import java.util.concurrent.Callable;

public class ThreadLocalContextManager<T> implements ContextManager<T> {

    private final ThreadLocal<T> value;

    public ThreadLocalContextManager() {
        value = new ThreadLocal<T>() {
            protected T initial() {
                return defaultValue();
            }
        };
    }

    protected T defaultValue() {
        return null;
    }

    public final T getCurrent() {
        return value.get();
    }

    public final void run(T context, Runnable runnable) {
        value.set(context);
        try {
            runnable.run();
        } finally {
            value.remove();
        }
    }

    public final <R> R call(T context, Callable<R> callable) throws Exception {
        value.set(context);
        try {
            return callable.call();
        } finally {
            value.remove();
        }
    }

}
