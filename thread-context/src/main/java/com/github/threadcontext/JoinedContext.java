package com.github.threadcontext;

import com.github.threadcontext.function.SupplierException1;
import com.github.threadcontext.function.SupplierException2;
import com.github.threadcontext.function.SupplierException3;
import com.github.threadcontext.function.SupplierException4;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class JoinedContext implements Context {

    private final Context context1;
    private final Context context2;

    public JoinedContext(Context context1, Context context2) {
        this.context1 = context1;
        this.context2 = context2;
    }

    public void run(Runnable runnable) {
        context1.run(() -> context2.run(runnable));
    }

    public <R> R call(Callable<R> callable) throws Exception {
        return context1.call(() -> context2.call(callable));
    }

    public <R> R supply(Supplier<R> supplier) {
        return context1.supply(() -> context2.supply(supplier));
    }

    public <R, E1 extends Throwable> R supplyException1(SupplierException1<R, E1> supplier) throws E1 {
        return context1.<R, E1>supplyException1(() -> context2.supplyException1(supplier));
    }

    public <R, E1 extends Throwable, E2 extends Throwable> R supplyException2(SupplierException2<R, E1, E2> supplier) throws E1, E2 {
        return context1.<R, E1, E2>supplyException2(() -> context2.supplyException2(supplier));
    }

    public <R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable> R supplyException3(SupplierException3<R, E1, E2, E3> supplier) throws E1, E2, E3 {
        return context1.<R, E1, E2, E3>supplyException3(() -> context2.supplyException3(supplier));
    }

    public <R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable> R supplyException4(SupplierException4<R, E1, E2, E3, E4> supplier) throws E1, E2, E3, E4 {
        return context1.<R, E1, E2, E3, E4>supplyException4(() -> context2.supplyException4(supplier));
    }

}
