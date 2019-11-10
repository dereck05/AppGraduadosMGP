package com.example.sistemagraduadosmgp;

import androidx.appcompat.app.AppCompatActivity;

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
        contra = findViewById(R.id.txt_passwordLogin);

        aceptar = findViewById(R.id.btn_crear);


        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(correo.getText().toString().length() > 0 && contra.getText().toString().length() > 0){

                    Toast.makeText(getApplicationContext(),"Usuario registrado con exito",Toast.LENGTH_LONG).show();
                    agregarUsuario();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Hay un campo sin rellenar",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void agregarUsuario(){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // SET CONNECTIONSTRING
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String username = "plataformamgp_SQLLogin_1";
            String password = "f29aa8wotf";
            Connection dbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://plataformamgp.mssql.somee.com/plataformamgp;user=" + username + ";password=" + password);

            Log.w("Connection","open");
            // Create CallableStatement object.
            String storedProcudureCall = "{call registrarUsuario(?,?,?,?)};";
            CallableStatement cStmt = dbConn.prepareCall(storedProcudureCall);

            // Set input parameters value.
            cStmt.setString(1, correo.getText().toString());
            cStmt.setString(2, contra.getText().toString());
            cStmt.setInt(3, 0);
            cStmt.setString(4,"usuario");
            // Execute stored procedure.
            boolean rs = cStmt.execute();

            System.out.println("Agregado");

            // Do not forget close Callabel Statement and db connection object.
            cStmt.close();

            dbConn.close();

        } catch (Exception e)
        {
            Log.w("Error connection","" + e.getMessage());
        }
    }
}
