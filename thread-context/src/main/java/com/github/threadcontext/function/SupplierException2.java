package com.github.threadcontext.function;

@FunctionalInterface
public interface SupplierException2<R, E1 extends Throwable, E2 extends Throwable> {

    R get() throws E1, E2;

}
