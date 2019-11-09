package com.example.sistemagraduadosmgp;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.EditText;

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


    public void connect(){
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
            cStmt.setString(1, "dereckescalante@hotmail.com");

            cStmt.setString(2, "hola");

            cStmt.setInt(3, 0);
            cStmt.setString(4, "administrador");
            // Execute stored procedure.
            boolean rs = cStmt.execute();

            System.out.println("Agregado");

            // Do not forget close Callabel Statement and db connection object.
            cStmt.close();

            dbConn.close();

        } catch (Exception e)
        {
            Log.w("Error connection","" + e.getMessage());        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connect();
    }


}
