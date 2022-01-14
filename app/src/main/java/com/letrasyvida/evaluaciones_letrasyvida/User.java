package com.letrasyvida.evaluaciones_letrasyvida;

public class User {
    private String Correo, Contrasena, Nombre, Apellido, Idnumber , Id_user , username;

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getId_user() {
        return Id_user;
    }

    public void setId_user(String id_user) {
        Id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdnumber() {
        return Idnumber;
    }

    public void setIdnumber(String idnumber) {
        Idnumber = idnumber;
    }
}
