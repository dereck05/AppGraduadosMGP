package com.example.sistemagraduadosmgp;

public final class usuario {
    static usuario usuario = null;
    static  String correo = null;
    static  String cedula;
    static String rol;

        private usuario(){

    }

    public static usuario getInstance(){
            if (usuario == null){

                usuario = new usuario();
            }
            return usuario;
    }
}
