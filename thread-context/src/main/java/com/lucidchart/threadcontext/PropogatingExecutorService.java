package com.lucidchart.threadcontext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class PropogatingExecutorService<T> implements ExecutorService {

    private final ContextManager<T> manager;
    private final ExecutorService delegate;

    public PropogatingExecutorService(ExecutorService delegate, ContextManager<T> manager) {
        this.delegate = delegate;
        this.manager = manager;
    }

    public final void shutdown() {
        delegate.shutdown();
    }

    public final List<Runnable> shutdownNow() {
        return delegate.shutdownNow();
    }

    public final boolean isShutdown() {
        return delegate.isShutdown();
    }

    public final boolean isTerminated() {
        return delegate.isTerminated();
    }

    public final boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        return delegate.awaitTermination(l, timeUnit);
    }

    public final <T2> Future<T2> submit(Callable<T2> callable) {
        final T context = manager.getCurrent();
        return delegate.submit(() -> manager.call(context, callable));
    }

    public final <T2> Future<T2> submit(Runnable runnable, T2 t) {
        final T context = manager.getCurrent();
        return delegate.submit(() -> manager.run(context, runnable), t);
    }

    public final Future<?> submit(Runnable runnable) {
        final T context = manager.getCurrent();
        return delegate.submit(() -> manager.run(context, runnable));
    }

    public <T2> List<Future<T2>> invokeAll(Collection<? extends Callable<T2>> collection) throws InterruptedException {
        final T context = manager.getCurrent();
        final ArrayList<Callable<T2>> propogatingCollection = new ArrayList<>(collection);
        propogatingCollection.replaceAll(callable -> () -> manager.call(context, callable));
        return delegate.invokeAll(propogatingCollection);
    }

    public <T2> List<Future<T2>> invokeAll(Collection<? extends Callable<T2>> collection, long l, TimeUnit timeUnit) throws InterruptedException {
        final T context = manager.getCurrent();
        final ArrayList<Callable<T2>> propogatingCollection = new ArrayList<>(collection);
        propogatingCollection.replaceAll(callable -> () -> manager.call(context, callable));
        return delegate.invokeAll(propogatingCollection, l, timeUnit);
    }

    public <T2> T2 invokeAny(Collection<? extends Callable<T2>> collection) throws InterruptedException, ExecutionException {
        final T context = manager.getCurrent();
        final ArrayList<Callable<T2>> propogatingCollection = new ArrayList<>(collection);
        propogatingCollection.replaceAll(callable -> () -> manager.call(context, callable));
        return delegate.invokeAny(propogatingCollection);
    }

    public <T2> T2 invokeAny(Collection<? extends Callable<T2>> collection, long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        final T context = manager.getCurrent();
        final ArrayList<Callable<T2>> propogatingCollection = new ArrayList<>(collection);
        propogatingCollection.replaceAll(callable -> () -> manager.call(context, callable));
        return delegate.invokeAny(propogatingCollection);
    }

    public void execute(Runnable runnable) {
        final T context = manager.getCurrent();
        delegate.execute(() -> manager.run(context, runnable));
    }

}
