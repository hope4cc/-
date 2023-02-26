package com.paopao.async.asyncRunner;

import com.paopao.async.RunnerQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseRunner implements AsyncRunner {

    @Autowired
    RunnerQueue runnerQueue;
}
