package com.example.sistemagraduadosmgp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ManejarOfertaCursos extends AppCompatActivity {

    private TableLayout tableLayout;
    private TableLayout tableLayout2;
    Spinner spinnerCurso;

    ArrayList<String> data = new ArrayList<>();
    ArrayList<Integer> indices = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manejar_oferta_cursos);

        spinnerCurso = (Spinner) findViewById(R.id.spinnerCurso);

        cargarNombresCursos();

    }


    public void cargarNombresCursos() {

        try {


            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // SET CONNECTIONSTRING
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String username = "plataformamgp_SQLLogin_1";
            String password = "f29aa8wotf";
            Connection dbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://plataformamgp.mssql.somee.com/plataformamgp;user=" + username + ";password=" + password);

            Log.w("Connection", "open");

            String storedProcedureCall = "{call obtenerNombresCursos()};";

            CallableStatement cStmt = dbConn.prepareCall(storedProcedureCall);

            ResultSet res = cStmt.executeQuery();

            while (res.next()) {
                String id = res.getString("nombreCurso");
                data.add(id);
                int indice = res.getInt("id");
                indices.add(indice);
            }


            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, data);
            NoCoreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCurso.setAdapter(NoCoreAdapter);

            // Do not forget close Callabel Statement and db connection object.
            cStmt.close();

            dbConn.close();

        } catch (Exception e) {
            Log.w("Error connection", "" + e.getMessage());
        }
    }


    public void cargarDatos(View v){

        //Encuenta el id del elemento selecionado
        String name = spinnerCurso.getSelectedItem().toString();
        int indiceSeleccion = -1;
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).equals(name)){
                indiceSeleccion = i;
            }

        }

        //Carga los datos basicos del curso
        cargarDatosBasicos(indiceSeleccion);

        //Carga los datos de la tabla con el historial de ofertas
        cargarTablaHistorialOfertas(indiceSeleccion);

        //Carga los datos de la tabla de interesados del curso
        cargarTablaInteresados(indiceSeleccion);
    }


    public void cargarDatosBasicos(int indiceId){

        try{

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // SET CONNECTIONSTRING
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String username = "plataformamgp_SQLLogin_1";
            String password = "f29aa8wotf";
            Connection dbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://plataformamgp.mssql.somee.com/plataformamgp;user=" + username + ";password=" + password);

            Log.w("Connection","open");

            String storedProcudureCall = "{call obtenerDatosBasicosDeCurso(?)};";
            CallableStatement cStmt = dbConn.prepareCall(storedProcudureCall);
            cStmt.setString(1, indices.get(indiceId).toString());

            ResultSet res = cStmt.executeQuery();

            TextView lblCodigo = findViewById(R.id.lblCodigo);
            TextView lblNombre = findViewById(R.id.lblNombre);
            TextView lblDescripcion = findViewById(R.id.lblDescripcion);
            TextView lblProfesor = findViewById(R.id.lblProfesor);
            TextView lblPeriodo = findViewById(R.id.lblPeriodo);
            TextView lblHorario = findViewById(R.id.lblHorario);
            TextView lblPrecio = findViewById(R.id.lblPrecio);



            while (res.next()) {
                lblCodigo.setText("" + (String) res.getString(1));
                lblNombre.setText("" + (String) res.getString(2));
                lblDescripcion.setText("" + (String) res.getString(4));
                lblProfesor.setText("" + (String) res.getString(6));
                lblPeriodo.setText("" + (String) res.getString(5));
                lblHorario.setText("" + (String) res.getString(7));
                lblPrecio.setText("" + (int) res.getFloat(3));

            }

            // Do not forget close Callabel Statement and db connection object.
            cStmt.close();

            dbConn.close();

        } catch (Exception e){
            Log.w("Error connection","" + e.getMessage());
        }



    }


    public void cargarTablaHistorialOfertas(int indiceId){

        try{

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // SET CONNECTIONSTRING
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String username = "plataformamgp_SQLLogin_1";
            String password = "f29aa8wotf";
            Connection dbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://plataformamgp.mssql.somee.com/plataformamgp;user=" + username + ";password=" + password);

            Log.w("Connection","open");

            String storedProcudureCall = "{call obtenerHistorialOfertasCurso(?)};";

            CallableStatement cStmt = dbConn.prepareCall(storedProcudureCall);
            cStmt.setString(1, indices.get(indiceId).toString());

            ResultSet res = cStmt.executeQuery();

            tableLayout=(TableLayout)findViewById(R.id.tabla_historial);

            tableLayout.removeAllViews();


            ConstraintLayout cl = findViewById(R.id.main);

            View tableRow;

            tableRow = LayoutInflater.from(this).inflate(R.layout.table_header_historial_ofertas,cl,false);
            tableLayout.addView(tableRow);

            while (res.next()){
                tableRow = LayoutInflater.from(this).inflate(R.layout.table_row_historial_ofertas,cl,false);

                TextView cedula  = (TextView) tableRow.findViewById(R.id.cedula);
                cedula.setText("" + (int)res.getFloat(1));

                TextView medio  = (TextView) tableRow.findViewById(R.id.medio);
                medio.setText("" + res.getString(4));

                TextView fecha = (TextView) tableRow.findViewById(R.id.fecha);
                fecha.setText("" + res.getDate(2));

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

    public void cargarTablaInteresados(int indiceId){
        try{

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // SET CONNECTIONSTRING
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String username = "plataformamgp_SQLLogin_1";
            String password = "f29aa8wotf";
            Connection dbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://plataformamgp.mssql.somee.com/plataformamgp;user=" + username + ";password=" + password);

            Log.w("Connection","open");

            String storedProcudureCall = "{call obtenerInteresadosEnCurso(?)};";

            CallableStatement cStmt = dbConn.prepareCall(storedProcudureCall);
            cStmt.setString(1, indices.get(indiceId).toString());

            ResultSet res = cStmt.executeQuery();

            tableLayout2=(TableLayout)findViewById(R.id.tabla_interesados);
            tableLayout2.removeAllViews();

            ConstraintLayout cl = findViewById(R.id.main);

            View tableRow;

            tableRow = LayoutInflater.from(this).inflate(R.layout.table_header_interesados,cl,false);
            tableLayout2.addView(tableRow);

            while (res.next()){
                tableRow = LayoutInflater.from(this).inflate(R.layout.table_row_interesados,cl,false);

                TextView nombre  = (TextView) tableRow.findViewById(R.id.nombre);
                nombre.setText("" + res.getString(2));

                TextView correo  = (TextView) tableRow.findViewById(R.id.correo);
                correo.setText("" + res.getString(3));

                TextView telefono = (TextView) tableRow.findViewById(R.id.telefono);
                telefono.setText("" + (int)res.getFloat(5));

                tableLayout2.addView(tableRow);

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
