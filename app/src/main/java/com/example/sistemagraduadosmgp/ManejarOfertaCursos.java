package com.example.sistemagraduadosmgp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ManejarOfertaCursos extends AppCompatActivity {

    private TableLayout tableLayout;
    Spinner spinnerCurso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manejar_oferta_cursos);

        spinnerCurso = (Spinner) findViewById(R.id.spinnerCurso);

    }


    public void cargarNombresCursos(View v){

        try{



            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // SET CONNECTIONSTRING
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String username = "plataformamgp_SQLLogin_1";
            String password = "f29aa8wotf";
            Connection dbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://plataformamgp.mssql.somee.com/plataformamgp;user=" + username + ";password=" + password);

            Log.w("Connection","open");

            String storedProcudureCall = "{call obtenerNombresCursos;";

            CallableStatement cStmt = dbConn.prepareCall(storedProcudureCall);

            ResultSet res = cStmt.executeQuery();

            ArrayList<String> data = new ArrayList<>();
            while(res.next()){
                String id = res.getString("nombreCurso");
                data.add(id);

            }

            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
            spinnerCurso.setAdapter(NoCoreAdapter);

        } catch (Exception e)
        {
            Log.w("Error connection","" + e.getMessage());
        }

        spinnerCurso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                String name = spinnerCurso.getSelectedItem().toString();
                Toast.makeText(ManejarOfertaCursos.this, name, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

//    public void cargarTablaHistorialOfertas(View v){
//
//        try{
//
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//
//            // SET CONNECTIONSTRING
//            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
//            String username = "plataformamgp_SQLLogin_1";
//            String password = "f29aa8wotf";
//            Connection dbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://plataformamgp.mssql.somee.com/plataformamgp;user=" + username + ";password=" + password);
//
//            Log.w("Connection","open");
//
//            String storedProcudureCall = "{call (?,?,?,?)};";
//
//
//        } catch (Exception e)
//        {
//            Log.w("Error connection","" + e.getMessage());
//        }
//    }

}
