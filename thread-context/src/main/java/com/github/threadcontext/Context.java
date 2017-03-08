package com.github.threadcontext;

import com.github.threadcontext.function.SupplierException1;
import com.github.threadcontext.function.SupplierException2;
import com.github.threadcontext.function.SupplierException3;
import com.github.threadcontext.function.SupplierException4;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public interface Context {

    MutableContextSupplier DEFAULT = new MutableContextSupplier();

    void run(Runnable runnable);

    <R> R call(Callable<R> callable) throws Exception;

    <R> R supply(Supplier<R> supplier);

    <R, E1 extends Throwable> R supplyException1(SupplierException1<R, E1> supplier) throws E1;

    <R, E1 extends Throwable, E2 extends Throwable> R supplyException2(SupplierException2<R, E1, E2> supplier) throws E1, E2;

    <R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable> R supplyException3(SupplierException3<R, E1, E2, E3> supplier) throws E1, E2, E3;

    <R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable> R supplyException4(SupplierException4<R, E1, E2, E3, E4> supplier) throws E1, E2, E3, E4;

    default Context andThen(Context context) {
        return new JoinedContext(this, context);
    }

}
