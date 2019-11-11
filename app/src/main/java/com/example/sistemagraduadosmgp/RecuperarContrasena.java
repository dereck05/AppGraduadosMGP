package com.example.sistemagraduadosmgp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;

public class RecuperarContrasena extends AppCompatActivity {
    EditText email;
    String newPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperarcontrasena);

        email = findViewById(R.id.txtReestablecer);
        Button enviarCorreo = findViewById(R.id.btnEnviarCorreo);

        enviarCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    // SET CONNECTIONSTRING
                    Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                    String username = "plataformamgp_SQLLogin_1";
                    String password = "f29aa8wotf";
                    Connection dbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://plataformamgp.mssql.somee.com/plataformamgp;user=" + username + ";password=" + password);

                    Log.w("Connection", "open");


                    String storedProcudureCall = "{call validarUsuario(?)};";

                    CallableStatement cStmt = dbConn.prepareCall(storedProcudureCall);
                    cStmt.setString(1, email.getText().toString());

                    ResultSet res = cStmt.executeQuery();

                    String rol = null;

                    while (res.next()) {

                        rol = res.getString(1);
                    }

                    usuario.getInstance().rol = rol;

                    if(usuario.rol.equals("Valido")){
                        newPassword = randomPasw();
                        String storedProcudureCall2 = "{call cambiarPassword(?,?)};";

                        CallableStatement cStmt2 = dbConn.prepareCall(storedProcudureCall2);
                        cStmt2.setString(1, email.getText().toString());
                        cStmt2.setString(2, newPassword);

                        ResultSet res2 = cStmt2.executeQuery();

                        String rol2 = null;

                        while (res2.next()) {

                            rol2 = res2.getString(1);
                        }

                        usuario.getInstance().rol = rol2;

                        if(usuario.rol.equals("Valido")){
                            Toast.makeText(getApplicationContext(),"Se ha cambiado la contrasena.",Toast.LENGTH_LONG).show();
                        } else{
                            Toast.makeText(getApplicationContext(),"Error.",Toast.LENGTH_LONG).show();
                        }

                    } else{
                        Toast.makeText(getApplicationContext(),"Error.",Toast.LENGTH_LONG).show();
                    }

                    cStmt.close();

                    dbConn.close();

                }catch(Exception e){
                    System.out.println("Error: "+ e);
                    Toast.makeText(getApplicationContext(),"Error no se ha podido crear." + e,Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void sendEmail(){
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
       // props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

       // session = Session.getDefaultInstance(props, this);
    }

    public String randomPasw(){
        String CharSet = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890!@#$";
        String Token = "";
        for (int a = 1; a <= 9; a++) {
            Token += CharSet.charAt(new Random().nextInt(CharSet.length()));
        }
        return Token;
    }
}
