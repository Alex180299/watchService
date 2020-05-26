package com.example;

import com.example.model.Usuario;
import com.example.threads.FileManager;
import com.example.threads.SaveUsuarios;

import java.io.IOException;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.Queue;

public class App {

    static FileManager fileManager;
    static Queue<Usuario> usuarioQueue = new LinkedList<>();
    static SaveUsuarios saveUsuarios = new SaveUsuarios(usuarioQueue);
    static String directory = "/Users/alejandroortizpablo/Documents/Development/work/watcherFile/";
    static String fileName = "usuarios.txt";
    static WatchService service;
    static Path path = Paths.get(directory);
    static boolean isRunning = false;
    static WatchKey key;

    public static void main() {
        fileManager = new FileManager(directory + fileName, usuarioQueue);
        try {
            service = FileSystems.getDefault().newWatchService();
            path.register(service, StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(() -> {
            try{
                while((key = service.take()) != null){
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if(event.context().toString().equals("usuarios.txt") && !isRunning){
                            fileManager.readLine();
                            doOperations();
                        }
                    }
                    key.reset();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        thread.start();
        doOperations();
        //fileManager.dispose();
    }

    public static void doOperations(){
        long start;
        isRunning = true;
        do {
            System.out.println("-> File manager");
            fileManager.reset();
            start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 10000) {
                fileManager.run();
            }

            System.out.println("-> Save usuarios");
            saveUsuarios.reset();
            start = System.currentTimeMillis();
            while ((System.currentTimeMillis() - start) < 20000) {
                saveUsuarios.run();
            }
        }while(!fileManager.isNoMore() || usuarioQueue.size() > 0);
        isRunning = false;
        System.out.println("Ya no hay más usuarios por leer ni en cola");
    }
}