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
import java.util.function.Supplier;

public class PropagatingExecutorService implements ExecutorService {

    private final ExecutorService delegate;
    private final Supplier<Context> contextSupplier;

    public PropagatingExecutorService(ExecutorService delegate, Supplier<Context> contextSupplier) {
        this.delegate = delegate;
        this.contextSupplier = contextSupplier;
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

    private <T> Callable<T> callable(Callable<T> callable) {
        Context context = contextSupplier.get();
        return () -> context.call(callable);
    }

    private Runnable runnable(Runnable runnable) {
        Context context = contextSupplier.get();
        return () -> context.run(runnable);
    }

    private <T> Collection<Callable<T>> callables(Collection<? extends Callable<T>> callables) {
        Context context = contextSupplier.get();
        Collection<Callable<T>> result = new ArrayList<>(callables.size());
        for (Callable<T> callable : callables) {
            result.add(() -> context.call(callable));
        }
        return result;
    }

    public <T> Future<T> submit(Callable<T> callable) {
        return delegate.submit(callable(callable));
    }

    public <T> Future<T> submit(Runnable runnable, T t) {
        return delegate.submit(runnable(runnable), t);
    }

    public Future<?> submit(Runnable runnable) {
        return delegate.submit(runnable(runnable));
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException {
        return delegate.invokeAll(callables(collection));
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException {
        return delegate.invokeAll(callables(collection), l, timeUnit);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
        return delegate.invokeAny(callables(collection));
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return delegate.invokeAny(callables(collection));
    }

    public void execute(Runnable runnable) {
        delegate.execute(runnable(runnable));
    }

}
