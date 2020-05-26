package com.example.repository;

import com.example.model.Usuario;

import java.util.List;

public interface UsuarioRepository {

    public List<Usuario> findAll();
    public Usuario findById(int id);
    public Usuario save(Usuario usuario);

}
