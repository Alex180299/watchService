package com.example;

import com.example.threads.FileManager;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.*;

public class Watch extends Thread{

    private String directory;
    private boolean change = false;
    private final FileManager fileManager;
    private WatchKey key;
    private WatchService service;

    public Watch(String directory, FileManager fileManager) {
        this.directory = directory;
        this.fileManager = fileManager;

        try {
            service = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(directory);
            path.register(service, StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        while((key = service.take()) != null){
            for (WatchEvent<?> event : key.pollEvents()) {
                if(event.context().toString().equals("usuarios.txt")){
                    System.out.println("Ha habido un evento en el archivo de usuarios: " + event.kind());

                }
            }
            key.reset();
        }
    }

    public boolean isChange() {
        return change;
    }
}
