package com.github.threadcontext.control;

import com.github.threadcontext.Context;
import com.github.threadcontext.function.SupplierException1;
import com.github.threadcontext.function.SupplierException2;
import com.github.threadcontext.function.SupplierException3;
import com.github.threadcontext.function.SupplierException4;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public final class TryFinallyContext implements Context {

    private final Supplier<Runnable> prepare;

    public TryFinallyContext(Supplier<Runnable> prepare) {
        this.prepare = prepare;
    }

    public void run(Runnable runnable) {
        Runnable catchFinally = prepare.get();
        try {
            runnable.run();
        } finally {
            catchFinally.run();
        }
    }

    public <R> R call(Callable<R> callable) throws Exception {
        Runnable catchFinally = prepare.get();
        try {
            return callable.call();
        } finally {
            catchFinally.run();
        }
    }

    public <R> R supply(Supplier<R> supplier) {
        Runnable catchFinally = prepare.get();
        try {
            return supplier.get();
        } finally {
            catchFinally.run();
        }
    }

    public <R, E1 extends Throwable> R supplyException1(SupplierException1<R, E1> supplier) throws E1 {
        Runnable catchFinally = prepare.get();
        try {
            return supplier.get();
        } finally {
            catchFinally.run();
        }
    }

    public <R, E1 extends Throwable, E2 extends Throwable> R supplyException2(SupplierException2<R, E1, E2> supplier) throws E1, E2 {
        Runnable catchFinally = prepare.get();
        try {
            return supplier.get();
        } finally {
            catchFinally.run();
        }
    }

    public <R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable> R supplyException3(SupplierException3<R, E1, E2, E3> supplier) throws E1, E2, E3 {
        Runnable catchFinally = prepare.get();
        try {
            return supplier.get();
        } finally {
            catchFinally.run();
        }
    }

    public <R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable> R supplyException4(SupplierException4<R, E1, E2, E3, E4> supplier) throws E1, E2, E3, E4 {
        Runnable catchFinally = prepare.get();
        try {
            return supplier.get();
        } finally {
            catchFinally.run();
        }
    }

}
