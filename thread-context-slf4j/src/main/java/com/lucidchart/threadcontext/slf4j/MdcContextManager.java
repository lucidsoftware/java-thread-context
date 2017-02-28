package com.lucidchart.threadcontext.slf4j;

import com.lucidchart.threadcontext.ContextManager;
import org.slf4j.MDC;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class MdcContextManager<T> implements ContextManager<T> {

    private final ContextManager<T> delegate;
    private final String key;
    private final Function<T, String> value;

    public MdcContextManager(ContextManager<T> delegate, String key, Function<T, String> value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        this.delegate = delegate;
        this.key = key;
        this.value = value;
    }

    public T getCurrent() {
        return delegate.getCurrent();
    }

    public void run(T context, Runnable runnable) {
        MDC.put(key, value.apply(context));
        try {
            delegate.run(context, runnable);
        } finally {
            MDC.remove(key);
        }
    }

    public <R> R call(T context, Callable<R> callable) throws Exception {
        MDC.put(key, value.apply(context));
        try {
            return delegate.call(context, callable);
        } finally {
            MDC.remove(key);
        }
    }

}
