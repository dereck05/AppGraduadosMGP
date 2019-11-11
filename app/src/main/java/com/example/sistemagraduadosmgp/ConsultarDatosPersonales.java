package com.example.sistemagraduadosmgp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ConsultarDatosPersonales extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_datos_personales);
        getDatosPersonales();
    }


    public void getDatosPersonales(){
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // SET CONNECTIONSTRING
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String username = "plataformamgp_SQLLogin_1";
            String password = "f29aa8wotf";
            Connection dbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://plataformamgp.mssql.somee.com/plataformamgp;user=" + username + ";password=" + password);

            Log.w("Connection","open");


            String storedProcudureCall = "{call obtieneDatos(?)};";
            int x = (int)usuario.cedula -3;
            CallableStatement cStmt = dbConn.prepareCall(storedProcudureCall);
            cStmt.setInt(1, x);

            ResultSet res = cStmt.executeQuery();


            if ( res.next() )
            {
                String nom = res.getString(1);
                int cedula = (int)res.getFloat(2);
                int carnet = (int)res.getFloat(3);
                String genero= res.getString(4);
                String correo = res.getString(5);
                int telCasa= (int)res.getFloat(6);
                int telCel = (int)res.getFloat(7);
                String provincia= res.getString(8);
                String distrito = res.getString(9);
                String canton= res.getString(10);
                String pais = res.getString(11);

                TextView tnom = (TextView)findViewById(R.id.nombre);
                tnom.setText(nom);
                TextView tCed = (TextView)findViewById(R.id.cedula);
                tCed.setText(String.valueOf(cedula));
                TextView tCar = (TextView)findViewById(R.id.carnet);
                tCar.setText(String.valueOf(carnet));
                TextView tGen = (TextView)findViewById(R.id.genero);
                tGen.setText(genero);
                TextView tCor = (TextView)findViewById(R.id.correo);
                tCor.setText(correo);
                TextView tTelca = (TextView)findViewById(R.id.casa);
                tTelca.setText(String.valueOf(telCasa));
                TextView tTelcel = (TextView)findViewById(R.id.cel);
                tTelcel.setText(String.valueOf(telCel));
                TextView prov = (TextView)findViewById(R.id.provincia);
                prov.setText(provincia);
                TextView cant = (TextView)findViewById(R.id.canton);
                cant.setText(canton);
                TextView dist = (TextView)findViewById(R.id.distrito);
                dist.setText(distrito);
                TextView pa = (TextView)findViewById(R.id.pais);
                pa.setText(pais);

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
