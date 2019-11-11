package com.example.sistemagraduadosmgp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void login(View v){
        final EditText correo = findViewById(R.id.txt_CoreoLogin);
        final EditText pass = findViewById(R.id.txt_passwordLogin);

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // SET CONNECTIONSTRING
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String username = "plataformamgp_SQLLogin_1";
            String password = "f29aa8wotf";
            Connection dbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://plataformamgp.mssql.somee.com/plataformamgp;user=" + username + ";password=" + password);

            Log.w("Connection", "open");


            String storedProcudureCall = "{call iniciarSesion(?,?)};";

            CallableStatement cStmt = dbConn.prepareCall(storedProcudureCall);
            cStmt.setString(1, correo.getText().toString());
            cStmt.setString(2, pass.getText().toString());

            ResultSet res = cStmt.executeQuery();

            String rol = null;

            while (res.next()) {

                rol = res.getString(1);
            }

            usuario.getInstance().rol = rol;
            usuario.getInstance().correo = correo.getText().toString();

            String storedProcudureCall2 = "{call ObtenerCedula(?)};";
            CallableStatement cStmt2 = dbConn.prepareCall(storedProcudureCall2);
            cStmt2.setString(1, correo.getText().toString());

            res = cStmt2.executeQuery();

            float ced = 0;
            while (res.next()) {

                ced = res.getFloat(1);
            }

            usuario.getInstance().cedula = ced;


            cStmt.close();

            dbConn.close();

            if(!usuario.rol.equals("Invalido")){
                if(usuario.getInstance().rol.contentEquals( "administra" )|| usuario.getInstance().rol.contentEquals("administrador")){
                    startActivity(new Intent(MainActivity.this, MenuAdmin.class));

                }
                if(usuario.getInstance().rol.contentEquals("consultor")){
                    startActivity(new Intent(MainActivity.this, MenuConsultor.class));

                }
                if(usuario.getInstance().rol.contentEquals("usuario")){
                    startActivity(new Intent(MainActivity.this, MenuGraduado.class));

                }
            }

            else{
                Toast.makeText(getApplicationContext(),"Usuario no existe",Toast.LENGTH_LONG).show();
            }




        }catch(Exception e){
            Log.w("Error connection","" + e.getMessage());
        }

    }
    public void callRegistro(View v) {
        Intent intent = new Intent(MainActivity.this, Registro.class);
        startActivity(intent);
    }


}
