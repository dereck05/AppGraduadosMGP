package com.example.sistemagraduadosmgp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ModificarDatosActuales extends AppCompatActivity {

    EditText nombre;
    EditText genero;
    EditText correo;
    EditText telefono;
    EditText celular;
    EditText provincia;
    EditText canton;
    EditText distrito;
    EditText pais;

    TextView carnet;
    TextView cedula;

    Button actualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_datos_actuales);

        nombre = findViewById(R.id.nombretxtDatos);
         genero = findViewById(R.id.genetotxtDatos);
         correo = findViewById(R.id.coreotxtDatos);
         telefono = findViewById(R.id.telefonotxtDatos);
         celular = findViewById(R.id.celulartxtDatos);
         provincia = findViewById(R.id.provinciatxtDatos);
         canton = findViewById(R.id.cantontxtDatos);
         distrito = findViewById(R.id.distritotxtDatos);
         pais = findViewById(R.id.paistxtDatos);

         carnet = findViewById(R.id.carnettxtView);
         cedula = findViewById(R.id.cedulatxtView);

        actualizar = findViewById(R.id.btn_guardardatos);


        cargarDatos();

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatos();
            }
        });
    }


    public void cargarDatos(){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // SET CONNECTIONSTRING
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String username = "plataformamgp_SQLLogin_1";
            String password = "f29aa8wotf";
            Connection dbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://plataformamgp.mssql.somee.com/plataformamgp;user=" + username + ";password=" + password);

            Log.w("Connection", "open");


            String storedProcudureCall = "{call obtieneDatos(?)};";

            CallableStatement cStmt = dbConn.prepareCall(storedProcudureCall);
            cStmt.setFloat(1, usuario.getInstance().cedula);

            ResultSet res = cStmt.executeQuery();


            String pnombre = null;
            String pgenero = null;
            String pcorreo = null;
            float ptelefono = 0;
            float pcelular = 0;
            String pprovincia = null;
            String pcanton = null;
            String pdistrito = null;
            String ppais = null;

            float pcarnet = 0;

            while (res.next()) {

                pnombre = res.getString(1);
                pcarnet = res.getFloat(3);
                pgenero = res.getString(4);

                pcorreo = res.getString(5);
                ptelefono = res.getFloat(6);
                pcelular = res.getFloat(7);

                pprovincia = res.getString(8);
                pdistrito = res.getString(9);
                pcanton = res.getString(10);
                ppais = res.getString(11);
            }
            cStmt.close();
            dbConn.close();

            nombre.setText(pnombre);
            carnet.setText(Float.toString(pcarnet));
            genero.setText(pgenero);
            correo.setText(pcorreo);
            telefono.setText(Float.toString(ptelefono));
            celular.setText(Float.toString(pcelular));
            provincia.setText(pprovincia);
            canton.setText(pcanton);
            distrito.setText(pdistrito);
            pais.setText(ppais);
            cedula.setText(Float.toString(usuario.getInstance().cedula));

        }catch(Exception e){
            Log.e("error de conexion",e.getMessage());
        }


    }

    public void actualizarDatos(){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // SET CONNECTIONSTRING
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String username = "plataformamgp_SQLLogin_1";
            String password = "f29aa8wotf";
            Connection dbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://plataformamgp.mssql.somee.com/plataformamgp;user=" + username + ";password=" + password);

            Log.w("Connection", "open");


            String storedProcudureCall = "{call editarInfoSp(?,?,?,?,?,?,?,?,?,?)};";

            String direc = provincia.getText().toString() +" " +" " + canton.getText().toString() + " " + distrito.getText().toString() + " " + pais.getText().toString();

            CallableStatement cStmt = dbConn.prepareCall(storedProcudureCall);
            cStmt.setFloat(1, usuario.getInstance().cedula);
            cStmt.setString(2,nombre.getText().toString());
            cStmt.setString(3, usuario.getInstance().correo);
            cStmt.setFloat(4,Float.parseFloat(telefono.getText().toString()));
            cStmt.setFloat(5,Float.parseFloat(celular.getText().toString()));
            cStmt.setString(6,provincia.getText().toString());
            cStmt.setString(7,canton.getText().toString());
            cStmt.setString(8,distrito.getText().toString());
            cStmt.setString(9,pais.getText().toString());
            cStmt.setString(10,direc);


            cStmt.close();
            dbConn.close();


        }catch(Exception e){
            Log.e("error de conexion",e.getMessage());
        }

    }
}
