package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.Normalizer;

public class TermosActivity extends AppCompatActivity {


    private Button buttonConcordo;
    private Button buttonNaoConcordo;
    private SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termos);

        buttonConcordo = (Button) findViewById(R.id.buttonConcordo);
        buttonNaoConcordo = (Button) findViewById(R.id.buttonNaoConcordo);
        preference = getSharedPreferences("MinhaConfig", MODE_PRIVATE);

        Boolean aceito = preference.getBoolean("aceito", false);
        if(aceito){
            Intent intent = new Intent(TermosActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        buttonConcordo = (Button) findViewById(R.id.buttonConcordo);

        buttonNaoConcordo.setOnClickListener(new View.OnClickListener() {
            @Override
            
            public void onClick(View v) {

                ShowDialog sd = new ShowDialog(TermosActivity.this);
                    sd.showMessage("O aceite é obrigatório para utilização do nosso App", "Atenção");




            }
        });

        buttonConcordo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preference.edit();
                editor.putBoolean("aceito",true);
                editor.apply();
                Intent intent = new Intent(TermosActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });



    }
}


