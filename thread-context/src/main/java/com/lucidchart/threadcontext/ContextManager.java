package com.lucidchart.threadcontext;

import java.util.concurrent.Callable;

public interface ContextManager<T> {

    T getCurrent();

    void run(T context, Runnable runnable);

    <R> R call(T context, Callable<R> callable) throws Exception;

}
