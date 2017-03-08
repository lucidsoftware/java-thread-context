package com.github.threadcontext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MutableContextSupplier implements Supplier<Context> {

    public final List<Supplier<Context>> suppliers;

    public MutableContextSupplier() {
        suppliers = new ArrayList<>();
    }

    public Context get() {
        return new CombinedContextSupplier(suppliers).get();
    }

}
