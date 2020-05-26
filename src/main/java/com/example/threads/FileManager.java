package com.example.threads;

import com.example.model.Usuario;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Queue;

public class FileManager extends Thread {

    private String lineString;
    private int lineNumber;
    private FileReader fileReader;
    private BufferedReader buffer;
    private Queue<Usuario> usuarios;
    private Usuario usuario;
    private boolean noMore = false;

    public FileManager(String fileName, Queue<Usuario> usuarios){
        this.usuarios = usuarios;

        try {
            this.fileReader = new FileReader(fileName);
            this.buffer = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        if (lineNumber < 20) {
            try {
                lineString = buffer.readLine();

                if(lineString != null && lineString != ""){
                    usuario = Usuario.builder()
                            .nombre(lineString.substring(0,20).trim())
                            .apellido(lineString.substring(20,50).trim())
                            .empresa(lineString.substring(50, 80).trim())
                            .escuela(lineString.substring(80,130).trim())
                            .edad(Integer.valueOf(lineString.substring(130, 132)))
                            .email(lineString.substring(132).trim())
                            .build();

                    usuarios.add(usuario);
                }else{
                    //System.out.println("---> No hay más registros en el archivo");
                    noMore = true;
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            lineNumber++;
        }
    }

    public void dispose(){
        try {
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isNoMore() {
        return noMore;
    }

    public void reset(){
        lineNumber = 0;
    }

    public void readLine(){
        try {
            buffer.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
