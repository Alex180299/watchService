package com.example.rowmapper;

import com.example.model.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRowMapper {

    public Usuario mapUsuario(ResultSet rs) {
        Usuario usuario = null;

        try {
            rs.next();

            usuario = Usuario.builder()
                    .id(rs.getInt("id"))
                    .nombre(rs.getString("nombre"))
                    .apellido(rs.getString("apellido"))
                    .empresa(rs.getString("empresa"))
                    .email(rs.getString("email"))
                    .escuela(rs.getString("escuela"))
                    .edad(rs.getInt("edad"))
                    .build();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return usuario;
    }

    public List<Usuario> mapUsuarios(ResultSet rs){
        List<Usuario> usuarios = new ArrayList<>();

        try{
            while (rs.next()){
                usuarios.add(mapUsuario(rs));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return usuarios;
    }

}
