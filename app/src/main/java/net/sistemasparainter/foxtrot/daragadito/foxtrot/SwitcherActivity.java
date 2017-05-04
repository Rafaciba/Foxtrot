package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SwitcherActivity extends AppCompatActivity {

    private Button buttonLogin;
    private Button buttonSignup;
    private Button buttonAbout;
    private Button buttonTermos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switcher);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        buttonAbout = (Button) findViewById(R.id.buttonAbout);
        buttonTermos = (Button) findViewById(R.id.buttonTermos);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SwitcherActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SwitcherActivity.this, CadastroActivity.class);
                startActivity(i);
            }
        });

        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SwitcherActivity.this, SobreActivity.class);
                startActivity(i);
            }
        });

        buttonTermos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SwitcherActivity.this, TermosActivity.class);
                startActivity(i);
            }
        });
    }
}
