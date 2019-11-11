package com.example.sistemagraduadosmgp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ResultadoBusqueda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_busqueda);
        cargarTabla();
    }
    public void cargarTabla(){
        try{
            TableLayout tableLayout;

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // SET CONNECTIONSTRING
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String username = "plataformamgp_SQLLogin_1";
            String password = "f29aa8wotf";
            Connection dbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://plataformamgp.mssql.somee.com/plataformamgp;user=" + username + ";password=" + password);

            Log.w("Connection","open");
            String tipo = usuario.tipoConsulta;

            String storedProcudureCall = "{call busquedaGeneralAPP(?,?,?,?)};";

            CallableStatement cStmt = dbConn.prepareCall(storedProcudureCall);

            if (tipo == "nombre"){
                cStmt.setString(1, usuario.dato);
                cStmt.setInt(2, 0);
                cStmt.setInt(3, 0);
                cStmt.setInt(4, 1);

            }
            if (tipo == "cedula"){
                cStmt.setString(1,"" );
                cStmt.setInt(2, Integer.valueOf(usuario.dato));
                cStmt.setInt(3, 0);
                cStmt.setInt(4, 2);
            }
            if (tipo == "carnet"){
                cStmt.setString(1, "");
                cStmt.setInt(2, 0);
                cStmt.setInt(3, Integer.valueOf(usuario.dato));
                cStmt.setInt(4, 3);
            }

            ResultSet res = cStmt.executeQuery();
            tableLayout=(TableLayout)findViewById(R.id.tabla_layout);
            ConstraintLayout cl = findViewById(R.id.main);
            View tableRow = LayoutInflater.from(this).inflate(R.layout.table_row,cl,false);
            TextView carnet  = (TextView) tableRow.findViewById(R.id.cedula);
            TextView cedula  = (TextView) tableRow.findViewById(R.id.carnet);
            TextView nombre  = (TextView) tableRow.findViewById(R.id.nombre);
            TextView genero  = (TextView) tableRow.findViewById(R.id.genero);
            cedula.setText("Cedula");
            carnet.setText("Carnet");
            nombre.setText("Nombre");
            genero.setText("Genero");
            tableLayout.addView(tableRow);

            while ( res.next() )
            {
                tableRow = LayoutInflater.from(this).inflate(R.layout.table_row,cl,false);
                carnet  = (TextView) tableRow.findViewById(R.id.cedula);
                cedula  = (TextView) tableRow.findViewById(R.id.carnet);
                nombre  = (TextView) tableRow.findViewById(R.id.nombre);
                genero  = (TextView) tableRow.findViewById(R.id.genero);
                cedula.setText(""+(int)res.getFloat(1));
                carnet.setText(""+(int)res.getFloat(2));
                nombre.setText(res.getString(3));
                genero.setText(res.getString(4));
                tableLayout.addView(tableRow);

            }


            // Do not forget close Callabel Statement and db connection object.
            cStmt.close();

            dbConn.close();
        } catch (Exception e)
        {
            Log.w("Error connection","" + e.getMessage());
        }


    }

}
