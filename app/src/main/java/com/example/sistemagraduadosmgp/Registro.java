package com.example.sistemagraduadosmgp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Registro extends AppCompatActivity {

    EditText correo;
    EditText id;
    EditText contra;
    Button aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        correo = findViewById(R.id.txt_correo);
        id = findViewById(R.id.txt_cedula);
        contra = findViewById(R.id.txt_password);

        aceptar = findViewById(R.id.btn_signup);


        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbConnection test = new DbConnection();
                if(test.doInBackground() /*correo.getText().toString().length() > 0*/){

                    Toast.makeText(getApplicationContext(),"Si funca",Toast.LENGTH_SHORT).show();
                    agregarUsuario();


                }
                else {

                    Toast.makeText(getApplicationContext(),"Hay un campo sin rellenar",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void agregarUsuario(){
        try{
            //correo, pass, estado, id, rol
            //DbConnection Db = new DbConnection();
            //Connection  con = Db.conected();
            /*PreparedStatement pst = con.prepareStatement("EXEC registrarUsuario ?,?,?,?,?");
            pst.setString(1,correo.getText().toString());
            pst.setString(2, contra.getText().toString());
            pst.setBoolean(3,true);
            pst.setString(4,id.getText().toString());
            pst.setString(5,"Usuario");
            pst.execute();
            */


            Toast.makeText(getApplicationContext(), "Registro exitoso",Toast.LENGTH_SHORT).show();
        }catch( Exception e/*SQLException e*/){
            Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

        }


    }
}
