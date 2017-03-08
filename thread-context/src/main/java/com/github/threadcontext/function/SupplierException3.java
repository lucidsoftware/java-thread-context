package com.github.threadcontext.function;

@FunctionalInterface
public interface SupplierException3<R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable> {

    R get() throws E1, E2, E3;

}
