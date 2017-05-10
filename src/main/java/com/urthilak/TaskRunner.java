package com.urthilak;


import com.urthilak.domain.Quote;
import com.urthilak.service.DseService;

public class TaskRunner implements Runnable {

    private final DseService dseService;

    private final Quote quote;

    public TaskRunner(DseService dseService, Quote quote) {
        this.dseService = dseService;
        this.quote = quote;
    }

    @Override
    public void run() {
        dseService.save(quote);

    }
}
