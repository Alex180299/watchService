package com.example.threads;

import com.example.model.Usuario;
import com.example.repository.UsuarioRepository;
import com.example.repository.UsuarioRepositoryImp;
import lombok.SneakyThrows;

import java.util.Queue;
import java.util.stream.Collectors;

public class SaveUsuarios extends Thread {

    private Queue<Usuario> usuarios;
    private UsuarioRepository usuarioRepository;
    private boolean finished = false;
    private int currentUser = 0;

    public SaveUsuarios(Queue<Usuario> usuarios){
        this.usuarios = usuarios;
        this.usuarioRepository = new UsuarioRepositoryImp();
    }

//    @SneakyThrows
//    @Override
//    public void run() {
//
//        for (int i = 0; i < 20 && usuarios.size() > 0; i++) {
//            usuarioRepository.save(usuarios.poll());
//        }
//        //usuarios.stream().limit(20).collect(Collectors.toList());
//    }

    @Override
    public void run() {
        if(currentUser < 20 && usuarios.size() > 0) {
            usuarioRepository.save(usuarios.poll());
            currentUser++;
        }else{
            finished = true;
        }
    }



    public boolean isFinished() {
        return finished;
    }

    public void reset() {
        currentUser = 0;
        finished = false;
    }
}
