package com.example.repository;

import com.example.connection.MySqlConnection;
import com.example.model.Usuario;
import com.example.querys.UsuarioQuerys;
import com.example.rowmapper.UsuarioRowMapper;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositoryImp implements UsuarioRepository {

    private MySqlConnection connection;
    private UsuarioRowMapper rowMapper;

    public UsuarioRepositoryImp(){
        rowMapper = new UsuarioRowMapper();
        connection = new MySqlConnection();
    }

    @Override
    public List<Usuario> findAll() {
        return rowMapper.mapUsuarios(connection.execute(UsuarioQuerys.findAll));
    }

    @Override
    public Usuario findById(int id) {
        List<String> params = new ArrayList<>();

        params.add(String.valueOf(id));
        return rowMapper.mapUsuario(connection.execute(UsuarioQuerys.findById, params));
    }

    @Override
    public Usuario save(Usuario usuario) {
        List<String> params = new ArrayList<>();

        params.add(usuario.getNombre());
        params.add(usuario.getApellido());
        params.add(usuario.getEmpresa());
        params.add(usuario.getEscuela());
        params.add(String.valueOf(usuario.getEdad()));
        params.add(usuario.getEmail());

        connection.executeUpdate(UsuarioQuerys.save, params);
        return usuario;
    }
}
