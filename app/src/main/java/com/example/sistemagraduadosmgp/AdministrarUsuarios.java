package com.example.sistemagraduadosmgp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class AdministrarUsuarios extends AppCompatActivity {
    private static final String LOG = "DEBUG";
    private static String ip = "SQL5045.site4now.net";
    private static String port = "1433";
    private static String classs = "net.sourceforge.jtds.jdbc.Driver";
    private static String db = "DB_A4CEA1_graduadosmgp";
    private static String un = "DB_A4CEA1_graduadosmgp_admin";
    private static String password = "graduados19";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_usuarios);


    }

    public void registrarAdmin(View v){
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
            String storedProcudureCall = "{call agregarAdminConsultor(?,?,?,?)};";

            CallableStatement cStmt = dbConn.prepareCall(storedProcudureCall);

            // Set input parameters value.
            cStmt.setString(1, getMail());

            cStmt.setString(2, getPassword());

            cStmt.setInt(3, 0);
            cStmt.setString(4, getTipo());
            // Execute stored procedure.
            ResultSet res = cStmt.executeQuery();
            String val = "";
            if (res.next()) {
                val = res.getString(1);
            }


            // Do not forget close Callabel Statement and db connection object.
            cStmt.close();

            dbConn.close();
            Log.w("Info",val);
            if(val.contentEquals("Valido")){
                Toast.makeText(AdministrarUsuarios.this, "Agregado.",
                        Toast.LENGTH_LONG).show();
            }
            if(val.contentEquals("Invalido")){
                Toast.makeText(AdministrarUsuarios.this, "El usuario ya existe.",
                        Toast.LENGTH_LONG).show();
            }

        } catch (Exception e)
        {
            Log.w("Error connection","" + e.getMessage());
        }

    }
    public String getMail(){
        EditText ed = findViewById(R.id.email);
        String res = ed.getText().toString();
        return res;
    }

    public String getPassword(){
        EditText ed = findViewById(R.id.Password);
        String res = ed.getText().toString();
        return res;
    }
    public String getTipo(){
        RadioGroup rg = findViewById(R.id.tipo);
        int selectedId = rg.getCheckedRadioButtonId();
        String res ="";
        Log.w("Num",String.valueOf(selectedId));
        if(selectedId == 2131230905){
            res =  "consultor";
        }
        if(selectedId == 2131230904){
            res = "administrador";
        }
        return res;

    }


}
