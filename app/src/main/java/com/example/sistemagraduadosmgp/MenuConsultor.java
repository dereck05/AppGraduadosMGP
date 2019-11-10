package com.example.sistemagraduadosmgp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuConsultor extends AppCompatActivity {

    Button agregarllamada = findViewById(R.id.regLlamadaConsultor);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_consultor);


        agregarllamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuConsultor.this, registrarLlamada.class);
                startActivity(intent);
            }
        });
    }
}
