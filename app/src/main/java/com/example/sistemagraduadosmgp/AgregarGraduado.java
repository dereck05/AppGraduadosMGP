package com.example.sistemagraduadosmgp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class AgregarGraduado extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregargraduado);

        final EditText nombre = findViewById(R.id.txt_nombre);
        final EditText cedula = findViewById(R.id.txt_cedula);
        final EditText genero = findViewById(R.id.txt_genero);
        final EditText carne = findViewById(R.id.txt_carne);
        final EditText correo = findViewById(R.id.txt_correo);
        final EditText telefono = findViewById(R.id.txt_telefono);
        final EditText celular = findViewById(R.id.txt_celular);
        final EditText provincia = findViewById(R.id.txt_provincia);
        final EditText canton = findViewById(R.id.txt_canton);
        final EditText distrito = findViewById(R.id.txt_distrito);
        final EditText pais = findViewById(R.id.txt_pais);
        final EditText direccion = findViewById(R.id.txt_direccion);
        final EditText acta = findViewById(R.id.txt_acta);
        final EditText plan = findViewById(R.id.txt_plan);
        final EditText carrera = findViewById(R.id.txt_carrera);
        final EditText sede = findViewById(R.id.txt_sede);
        final EditText grado = findViewById(R.id.txt_grado);
        final EditText titulo = findViewById(R.id.txt_titulo);
        final EditText enfasis = findViewById(R.id.txt_enfasis);
        final EditText fecha = findViewById(R.id.txt_fecha);

        Button crear = findViewById(R.id.btn_crear);

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    // SET CONNECTIONSTRING
                    Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                    String username = "plataformamgp_SQLLogin_1";
                    String password = "f29aa8wotf";
                    Connection dbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://plataformamgp.mssql.somee.com/plataformamgp;user=" + username + ";password=" + password);

                    Log.w("Connection", "open");


                    String storedProcudureCall = "{call agregarEstudiante(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)};";

                    CallableStatement cStmt = dbConn.prepareCall(storedProcudureCall);
                    cStmt.setString(1, cedula.getText().toString());
                    cStmt.setString(2, carne.getText().toString());
                    cStmt.setString(3, nombre.getText().toString());
                    cStmt.setString(4, genero.getText().toString());
                    cStmt.setString(5, correo.getText().toString());
                    cStmt.setString(6, telefono.getText().toString());
                    cStmt.setString(7, celular.getText().toString());
                    cStmt.setString(8, provincia.getText().toString());
                    cStmt.setString(9, canton.getText().toString());
                    cStmt.setString(10, distrito.getText().toString());
                    cStmt.setString(11, pais.getText().toString());
                    cStmt.setString(12, direccion.getText().toString());
                    cStmt.setString(13, acta.getText().toString());
                    cStmt.setString(14, plan.getText().toString());
                    cStmt.setString(15, carrera.getText().toString());
                    cStmt.setString(16, sede.getText().toString());
                    cStmt.setString(17, grado.getText().toString());
                    cStmt.setString(18, titulo.getText().toString());
                    cStmt.setString(19, enfasis.getText().toString());
                    cStmt.setString(20, fecha.getText().toString());

                    ResultSet res = cStmt.executeQuery();

                    String rol = null;

                    while (res.next()) {

                        rol = res.getString(1);
                    }

                    usuario.getInstance().rol = rol;

                    if(!usuario.rol.equals("Invalido")){
                        Toast.makeText(getApplicationContext(),"Se ha creado el graduado.",Toast.LENGTH_LONG).show();
                    } else{
                        Toast.makeText(getApplicationContext(),"Error.",Toast.LENGTH_LONG).show();
                    }

                    cStmt.close();

                    dbConn.close();

                }catch(Exception e){
                    System.out.println("Error: "+ e);
                    Toast.makeText(getApplicationContext(),"Error no se ha podido crear." + e,Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
