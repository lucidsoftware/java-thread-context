package com.lucidchart.threadcontext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class ContextManager {

    private ContextManager() {
    }

    public static final Collection<Saver> savers = new ArrayList<>();

    public static Runnable runnable(Runnable runnable) {
        Collection<Runnable> saved1 = savers.stream().map(Saver::save).collect(Collectors.toList());
        return () -> {
            Collection<Runnable> saved2 = savers.stream().map(Saver::save).collect(Collectors.toList());
            saved1.forEach(Runnable::run);
            try {
                runnable.run();
            } finally {
                saved2.forEach(Runnable::run);
            }
        };
    }

    public static <R> Callable<R> callable(Callable<R> callable) {
        Collection<Runnable> saved1 = savers.stream().map(Saver::save).collect(Collectors.toList());
        return () -> {
            Collection<Runnable> saved2 = savers.stream().map(Saver::save).collect(Collectors.toList());
            saved1.forEach(Runnable::run);
            try {
                return callable.call();
            } finally {
                saved2.forEach(Runnable::run);
            }
        };
    }

}
