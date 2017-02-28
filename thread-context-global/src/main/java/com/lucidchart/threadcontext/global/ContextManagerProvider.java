package com.lucidchart.threadcontext.global;

import com.lucidchart.threadcontext.ContextManager;

public interface ContextManagerProvider {
    <T> ContextManager<T> get(Class<T> klass);
}
