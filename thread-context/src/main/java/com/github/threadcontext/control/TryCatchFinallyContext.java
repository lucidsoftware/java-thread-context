package com.github.threadcontext.control;

import com.github.threadcontext.Context;
import com.github.threadcontext.function.SupplierException1;
import com.github.threadcontext.function.SupplierException2;
import com.github.threadcontext.function.SupplierException3;
import com.github.threadcontext.function.SupplierException4;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public final class TryCatchFinallyContext implements Context {

    private final Supplier<CatchFinally> prepare;

    public TryCatchFinallyContext(Supplier<CatchFinally> prepare) {
        this.prepare = prepare;
    }

    public void run(Runnable runnable) {
        CatchFinally catchFinally = prepare.get();
        try {
            runnable.run();
        } catch (Throwable t) {
            catchFinally.except(t);
            throw t;
        } finally {
            catchFinally.ensure();
        }
    }

    public <R> R call(Callable<R> callable) throws Exception {
        CatchFinally catchFinally = prepare.get();
        try {
            return callable.call();
        } catch (Throwable t) {
            catchFinally.except(t);
            throw t;
        } finally {
            catchFinally.ensure();
        }
    }

    public <R> R supply(Supplier<R> supplier) {
        CatchFinally catchFinally = prepare.get();
        try {
            return supplier.get();
        } catch (Throwable t) {
            catchFinally.except(t);
            throw t;
        } finally {
            catchFinally.ensure();
        }
    }

    public <R, E1 extends Throwable> R supplyException1(SupplierException1<R, E1> supplier) throws E1 {
        CatchFinally catchFinally = prepare.get();
        try {
            return supplier.get();
        } catch (Throwable t) {
            catchFinally.except(t);
            throw t;
        } finally {
            catchFinally.ensure();
        }
    }

    public <R, E1 extends Throwable, E2 extends Throwable> R supplyException2(SupplierException2<R, E1, E2> supplier) throws E1, E2 {
        CatchFinally catchFinally = prepare.get();
        try {
            return supplier.get();
        } catch (Throwable t) {
            catchFinally.except(t);
            throw t;
        } finally {
            catchFinally.ensure();
        }
    }

    public <R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable> R supplyException3(SupplierException3<R, E1, E2, E3> supplier) throws E1, E2, E3 {
        CatchFinally catchFinally = prepare.get();
        try {
            return supplier.get();
        } catch (Throwable t) {
            catchFinally.except(t);
            throw t;
        } finally {
            catchFinally.ensure();
        }
    }

    public <R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable> R supplyException4(SupplierException4<R, E1, E2, E3, E4> supplier) throws E1, E2, E3, E4 {
        CatchFinally catchFinally = prepare.get();
        try {
            return supplier.get();
        } catch (Throwable t) {
            catchFinally.except(t);
            throw t;
        } finally {
            catchFinally.ensure();
        }
    }

}
