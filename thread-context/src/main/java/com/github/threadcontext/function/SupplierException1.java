package com.github.threadcontext.function;

@FunctionalInterface
public interface SupplierException1<R, E1 extends Throwable> {

    R get() throws E1;

}
