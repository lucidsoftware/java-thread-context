package com.lucidchart.threadcontext.global;

import com.lucidchart.threadcontext.ContextManager;
import java.util.concurrent.ConcurrentHashMap;

public class CachingContextManagerProvider implements ContextManagerProvider {

    private final ContextManagerProvider delegate;
    private final ConcurrentHashMap<Class<?>, ContextManager<?>> cache;

    public CachingContextManagerProvider(ContextManagerProvider delegate) {
        this.delegate = delegate;
        cache = new ConcurrentHashMap<>();
    }

    public <T> ContextManager<T> get(Class<T> klass) {
        return (ContextManager<T>)cache.computeIfAbsent(klass, delegate::get);
    }

}
