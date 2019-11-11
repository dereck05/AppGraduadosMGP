package com.example.sistemagraduadosmgp;

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

public class ManejarCursos extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manejarcursos);

        final EditText codigo = findViewById(R.id.txt_codigo);
        final EditText nombre = findViewById(R.id.txt_nombre);
        final EditText precio = findViewById(R.id.txt_precio);
        final EditText descripcion = findViewById(R.id.txt_descripcion);
        final EditText periodo = findViewById(R.id.txt_periodo);
        final EditText profesor = findViewById(R.id.txt_profesor);
        final EditText horario = findViewById(R.id.txt_horario);

        Button crearCurso = findViewById(R.id.btn_crear);

        crearCurso.setOnClickListener(new View.OnClickListener() {
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


                    String storedProcudureCall = "{call agregarCurso(?,?,?,?,?,?,?)};";

                    CallableStatement cStmt = dbConn.prepareCall(storedProcudureCall);
                    cStmt.setString(1, codigo.getText().toString());
                    cStmt.setString(2, nombre.getText().toString());
                    cStmt.setString(3, precio.getText().toString());
                    cStmt.setString(4, descripcion.getText().toString());
                    cStmt.setString(5, periodo.getText().toString());
                    cStmt.setString(6, profesor.getText().toString());
                    cStmt.setString(7, horario.getText().toString());

                    ResultSet res = cStmt.executeQuery();

                    String rol = null;

                    while (res.next()) {

                        rol = res.getString(1);
                    }

                    usuario.getInstance().rol = rol;

                    if(!usuario.rol.equals("Invalido")){
                        Toast.makeText(getApplicationContext(),"Se ha creado el curso.",Toast.LENGTH_LONG).show();
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
