package com.example.querys;

public class UsuarioQuerys {

    public static final String findAll = "SELECT * FROM usuarios;";
    public static final String findById = "SELECT * FROM usuarios WHERE id = ?;";
    public static final String save = "INSERT INTO usuarios(nombre, apellido, empresa, escuela, edad, email) VALUES (?, ?, ?, ?, ?, ?);";

}
