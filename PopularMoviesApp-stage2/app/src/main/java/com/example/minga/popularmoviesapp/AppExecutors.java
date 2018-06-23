package com.example.minga.popularmoviesapp;

import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static android.os.Looper.*;

/**
 * Created by minga on 6/21/2018.
 */

public class AppExecutors {

    // For Singleton instantiation
    private static final Object LOCK = new Object ();
    private static AppExecutors sInstance;
    private final Executor diskIO;
    private final Executor mainThread;
    private final Executor networkIO;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread){
        this.diskIO = diskIO;
        this.mainThread = mainThread;
        this.networkIO = networkIO;
    }

    public static AppExecutors getsInstance(){
        if (sInstance == null){
            synchronized(LOCK){
                sInstance=new AppExecutors (Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor diskIO(){return diskIO;}
    public Executor mainThread(){return mainThread;}
    public Executor networkIO(){return networkIO;}

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler () {
            @Override
            public void publish(LogRecord logRecord) {

            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        };

        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.publish (( LogRecord ) runnable);
        }
    }

}
