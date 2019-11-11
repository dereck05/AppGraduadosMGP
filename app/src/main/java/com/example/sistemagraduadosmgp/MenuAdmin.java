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
        regLlamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAdmin.this, registrarLlamada.class);
                startActivity(intent);
            }
        });
    }

    public void callConsultarDatos(View v){
        startActivity(new Intent(MenuAdmin.this, ConsultarDatosGenerales.class));
    }
}
