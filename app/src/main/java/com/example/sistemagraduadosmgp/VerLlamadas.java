package com.example.sistemagraduadosmgp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class VerLlamadas extends AppCompatActivity {

    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_llamadas);
    }

    public void cargarLlamadas(View v){

        try{

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // SET CONNECTIONSTRING
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String username = "plataformamgp_SQLLogin_1";
            String password = "f29aa8wotf";
            Connection dbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://plataformamgp.mssql.somee.com/plataformamgp;user=" + username + ";password=" + password);

            Log.w("Connection","open");

            String storedProcudureCall = "{call verLlamadas(?)};";

            CallableStatement cStmt = dbConn.prepareCall(storedProcudureCall);

            EditText noCedula = findViewById(R.id.txtCedula);
            String ced = noCedula.getText().toString();
            cStmt.setString(1, ced);

            ResultSet res = cStmt.executeQuery();

            tableLayout=(TableLayout)findViewById(R.id.tabla_llamadas);

            tableLayout.removeAllViews();


            ConstraintLayout cl = findViewById(R.id.main);

            View tableRow;

            tableRow = LayoutInflater.from(this).inflate(R.layout.table_header_llamadas,cl,false);
            tableLayout.addView(tableRow);

            while (res.next()){
                tableRow = LayoutInflater.from(this).inflate(R.layout.table_row_llamadas,cl,false);

                TextView cedula  = (TextView) tableRow.findViewById(R.id.cedula);
                cedula.setText("" + (int)res.getFloat(1));

                TextView fecha = (TextView) tableRow.findViewById(R.id.fecha);
                fecha.setText("" + res.getDate(2));

                TextView motivo  = (TextView) tableRow.findViewById(R.id.motivo);
                motivo.setText("" + res.getString(3));

                TextView comentarios  = (TextView) tableRow.findViewById(R.id.comentarios);
                comentarios.setText("" + res.getString(4));



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
