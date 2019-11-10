package com.example.sistemagraduadosmgp;

public class ClassListItem {
    public String Pasaporte;
    public String Nombre;
    public String Genero;
    public float Cedula;
    public float Carnet;
    public ClassListItem(float carnet,float cedula,String nombre,String pasaporte,String genero){
        this.Carnet = carnet;
        this.Cedula = cedula;
        this.Nombre = nombre;
        this.Pasaporte = pasaporte;
        this.Genero = genero;
    }
    public ClassListItem(){};
}

