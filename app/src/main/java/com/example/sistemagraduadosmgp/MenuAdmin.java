package com.example.sistemagraduadosmgp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuAdmin extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);


        Button regLlamada = findViewById(R.id.regLlamadaAdmin);
        Button actualizarDatos = findViewById(R.id.btnActualizarDatos);
        Button manejarCursos = findViewById(R.id.btnManejarCursos);
        Button consultarDatos = findViewById(R.id.consultarDatosbtn);
        Button administrarUsuarios = findViewById(R.id.adminUserbtn);
        Button manejarOfertasDeCurso = findViewById(R.id.btnManejarOfertasDeCurso);
        Button agregarGraduado = findViewById(R.id.btnAgregarGraduado);
        Button verLlamada = findViewById(R.id.btnVerLlamada);

        regLlamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAdmin.this, registrarLlamada.class);
                startActivity(intent);
            }
        });

        actualizarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAdmin.this, ActualizarDatos.class);
                startActivity(intent);
            }
        });

        manejarCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAdmin.this, ManejarCursos.class);
                startActivity(intent);
            }
        });

        consultarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAdmin.this, ConsultarDatosGenerales.class);
                startActivity(intent);
            }
        });

        administrarUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAdmin.this, AdministrarUsuarios.class);
                startActivity(intent);
            }
        });

        manejarOfertasDeCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAdmin.this, ManejarOfertaCursos.class);
                startActivity(intent);
            }
        });

        agregarGraduado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAdmin.this, AgregarGraduado.class);
                startActivity(intent);
            }
        });

        verLlamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAdmin.this, VerLlamadas.class);
                startActivity(intent);
            }
        });

    }

}
