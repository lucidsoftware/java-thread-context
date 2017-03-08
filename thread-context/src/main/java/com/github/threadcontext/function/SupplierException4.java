package com.github.threadcontext.function;

@FunctionalInterface
public interface SupplierException4<R, E1 extends Throwable, E2 extends Throwable, E3 extends Throwable, E4 extends Throwable> {

    R get() throws E1, E2, E3, E4;

}
