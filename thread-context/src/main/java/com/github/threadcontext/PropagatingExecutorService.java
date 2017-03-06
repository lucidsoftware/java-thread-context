package com.github.threadcontext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class PropagatingExecutorService implements ExecutorService {

    private final ExecutorService delegate;

    public PropagatingExecutorService(ExecutorService delegate) {
        this.delegate = delegate;
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

    public final <T> Future<T> submit(Callable<T> callable) {
        return delegate.submit(ThreadContext.callable(callable));
    }

    public final <T> Future<T> submit(Runnable runnable, T t) {
        return delegate.submit(ThreadContext.runnable(runnable), t);
    }

    public final Future<?> submit(Runnable runnable) {
        return delegate.submit(ThreadContext.runnable(runnable));
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException {
        final List<Callable<T>> propagatingCollection = new ArrayList<>(collection);
        propagatingCollection.replaceAll(ThreadContext::callable);
        return delegate.invokeAll(propagatingCollection);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException {
        final List<Callable<T>> propagatingCollection = new ArrayList<>(collection);
        propagatingCollection.replaceAll(ThreadContext::callable);
        return delegate.invokeAll(propagatingCollection, l, timeUnit);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
        final List<Callable<T>> propagatingCollection = new ArrayList<>(collection);
        propagatingCollection.replaceAll(ThreadContext::callable);
        return delegate.invokeAny(propagatingCollection);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        final List<Callable<T>> propagatingCollection = new ArrayList<>(collection);
        propagatingCollection.replaceAll(ThreadContext::callable);
        return delegate.invokeAny(propagatingCollection);
    }

    public void execute(Runnable runnable) {
        delegate.execute(ThreadContext.runnable(runnable));
    }

}
