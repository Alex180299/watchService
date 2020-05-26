package com.example.observer;

import com.sun.tools.javac.comp.Flow;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.*;

public class AppObserver {

    static BufferedReader bufferedReader;

    public void main() {

        try {
            FileReader fileReader = new FileReader("/Users/alejandroortizpablo/Documents/Development/work/watcherFile/usuarios.txt");
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Observable<WatchEvent> observable = Observable.create(subscriber -> {
            final Map<WatchKey, Path> directoriesByKey = new HashMap<>();

            Path directory = Paths.get("/Users/alejandroortizpablo/Documents/Development/work/watcherFile/");
            WatchService watcher = directory.getFileSystem().newWatchService();
            final WatchKey key = directory.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
            directoriesByKey.put(key, directory);

            while(!subscriber.isDisposed()){
                final WatchKey key2;
                try {
                    key2 = watcher.take();
                } catch (InterruptedException exception) {
                    if (!subscriber.isDisposed()) {
                        subscriber.onError(exception);
                    }
                    break;
                }

                final Path dir = directoriesByKey.get(key2);
                for (final WatchEvent<?> event : key2.pollEvents()) {
                    subscriber.onNext(event);
                }

                boolean valid = key2.reset();
                if (!valid) {
                    directoriesByKey.remove(key2);
                    if (directoriesByKey.isEmpty()) {
                        break;
                    }
                }
            }
        });


//        observable.subscribe(event -> {
//            bufferedReader.readLine();
//            System.out.println(Thread.currentThread().getName());
//            System.out.println(bufferedReader.readLine());
//        });

        observable
                .filter(o -> o.kind().equals(ENTRY_MODIFY))
                .filter(o -> o.context().toString().equals("usuarios.txt"))
                .doOnNext(watchEvent -> System.out.println(watchEvent.context() + " " + watchEvent.kind()))
                .subscribe();
    }

    public void observeList(){
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));

        Observable<Integer> observableList = Observable.fromIterable(list);

        observableList.subscribe(
                item -> System.out.println(item),
                error -> error.printStackTrace(),
                () -> System.out.println("Done"));
    }

    public void observeClock(){
//        Observable<Long> clock = Observable.interval(1, TimeUnit.SECONDS);
//
//        clock.subscribe(time -> System.out.println(time));
//
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Observable<Long> eggTimer = Observable.timer(5, TimeUnit.SECONDS);

        eggTimer.blockingSubscribe(v -> System.out.println("Egg is ready!"));
    }

}
