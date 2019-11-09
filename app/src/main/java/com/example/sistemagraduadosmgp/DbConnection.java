package com.example.sistemagraduadosmgp;

import android.os.AsyncTask;
import android.util.Log;
import java.sql.Connection;

import java.sql.DriverManager;

class DbConnection extends AsyncTask<Void, Integer, Boolean> {

    Connection Overconn = null;

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            String url = "jdbc:jtds:sqlserver://SQL5047.site4now.net/DB_A4FABB_sistemamgp;USER= DB_A4FABB_sistemamgp_admin;PASSWORD=plataforma19";
            Log.i("url", url);
            Class.forName( "net.sourceforge.jtds.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(url);
            Overconn = conn;
            if (conn == null)
            {

                return false;
            }
        } catch (NoClassDefFoundError e){
            Log.e("Definicion de clase",e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("Clase no encontrada",e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR Conexion:",e.getMessage());
            return false;
        }
        return true;
    }
    @Override
    protected void onPostExecute(Boolean resultado) {
        if(resultado) {
            Log.e("LOG:", "conectado");
        }else {
            Log.e("LOG:", "no conectado");
        }
    }

    public Connection conected(){
        if(Overconn == null){

            doInBackground();
        }
        return Overconn;

    }

}






