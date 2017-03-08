package com.github.threadcontext;

import java.util.concurrent.ExecutorService;

public class DefaultPropagatingExecutorService extends PropagatingExecutorService {

    public DefaultPropagatingExecutorService(ExecutorService delegate) {
        super(delegate, Context.DEFAULT);
    }

}
