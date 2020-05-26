package com.example.model;

import lombok.*;

@Data
@Builder
public class Usuario {

    private int id;
    private String nombre;
    private String apellido;
    private String empresa;
    private String escuela;
    private int edad;
    private String email;


}
