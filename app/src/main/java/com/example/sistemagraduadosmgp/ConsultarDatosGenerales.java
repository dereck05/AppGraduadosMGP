package com.example.sistemagraduadosmgp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
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

public class ConsultarDatosGenerales extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_datos_generales);
    }

    public void callResult(View v){
        usuario.getInstance().dato = getDato();
        usuario.getInstance().tipoConsulta = getTipo();
        Intent intent = new Intent(ConsultarDatosGenerales.this,ResultadoBusqueda.class);
        startActivity(intent);
    }
    public String getDato(){
        EditText ed = findViewById(R.id.dato);
        String res = ed.getText().toString();
        return res;
    }

    public String getTipo(){
        RadioGroup rg = findViewById(R.id.tipoConsulta);
        int selectedId = rg.getCheckedRadioButtonId();

        String res ="";
        Log.w("Num",String.valueOf(selectedId));
        if(selectedId == R.id.name){
            res =  "nombre";
        }
        if(selectedId == R.id.carnet){
            res = "carnet";
        }
        if(selectedId == R.id.cedula){
            res = "cedula";
        }
        return res;

    }



}
