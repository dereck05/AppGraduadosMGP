package com.example.sistemagraduadosmgp;

import android.os.AsyncTask;
import android.util.Log;
import java.sql.Connection;

import java.sql.DriverManager;

class DbConnection  {

    Connection conn = null;


    public Connection connect() {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            String username = "plataformamgp_SQLLogin_1";
            String password = "f29aa8wotf";
            Connection dbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://plataformamgp.mssql.somee.com/plataformamgp;user=" + username + ";password=" + password);
            conn = dbConn;
            if (dbConn == null)
            {
                return null;
            }
        } catch (NoClassDefFoundError e){
            Log.e("Definicion de clase",e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("Clase no encontrada",e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR Conexion:",e.getMessage());
            return null;
        }
        return conn;
    }


}






