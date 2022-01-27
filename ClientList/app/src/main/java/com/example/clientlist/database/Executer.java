package com.example.clientlist.database;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;

//создание второстепенного потока
public class Executer {
    public static  final Object LOCK = new Object();
    private static Executer instanceEx;
    private final Executor discIO;
    private final Executor mainIO;
    private final Executor networkIO;


    public Executer(Executor discIO, Executor mainIO, Executor networkIO) {
        this.discIO = discIO; //второстепенный поток
        this.mainIO = mainIO; //основной поток
        this.networkIO = networkIO; //второстепенный поток
    }

    public static Executer getInstance() {
        if(instanceEx == null) {
            synchronized (LOCK)
            {
                instanceEx = new Executer(Executors.newSingleThreadExecutor(), new ThreadHandler(),
                        Executors.newFixedThreadPool(3));
            }
        }
        return instanceEx;
    }

    //переносим данные из второстепенного потого в основной
    private static class ThreadHandler implements Executor {
        private android.os.Handler threadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable runnable) {
         threadHandler.post(runnable);
        }
    }

    public Executor getDiscIO() {
        return discIO;
    }

    public Executor getMainIO() {
        return mainIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }
}
