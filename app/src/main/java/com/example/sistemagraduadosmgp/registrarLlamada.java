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
import java.sql.Date;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class registrarLlamada extends AppCompatActivity {
    EditText cedula;
    EditText fecha;
    EditText motivo;
    EditText comentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_llamada);

        cedula = findViewById(R.id.txt_cedulaLlamada);

        motivo = findViewById(R.id.txt_MotivoLlamada);
        comentario = findViewById(R.id.txt_ComentarioLlamada);

        Button reg = findViewById(R.id.btn_regLlamada);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cedula.getText().toString().length() > 0 &&

                motivo.getText().toString().length() > 0){

                    registrarllamada();
                }
                else{

                    Toast.makeText(getApplicationContext(),"Hay un campo sin rellenar",Toast.LENGTH_LONG).show();
                }
            }
        });


    }



    public void registrarllamada(){
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
            String storedProcudureCall = "{call registrarLlamadasp(?,?,?,?)};";
            CallableStatement cStmt = dbConn.prepareCall(storedProcudureCall);

            // Set input parameters value.
            float fCedula = Float.parseFloat(cedula.getText().toString());

            Calendar calendar = Calendar.getInstance();
            java.util.Date date = calendar.getTime();
            java.sql.Date fFecha = new java.sql.Date(date.getTime());

            cStmt.setFloat(1, fCedula);
            cStmt.setString(2, motivo.getText().toString());
            cStmt.setDate(3,  fFecha);
            cStmt.setString(4,comentario.getText().toString());
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
