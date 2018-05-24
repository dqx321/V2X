package com.intelligent.v2xapp.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by daiqinxue on 17/1/16.
 */

public class ExecutorUtil {

    private static ExecutorService service = Executors.newCachedThreadPool();

//    private static ThreadPoolExecutor threadPool = null;

    public static synchronized void execute(Runnable runner) {

        service.execute(runner);

    }


}
