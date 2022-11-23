package com.maik.org.lab_04;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.maik.org.lab_04.DAO.PessoaDAO;
import com.maik.org.lab_04.model.Pessoa;

import java.io.Serializable;

public class MenuActivity extends AppCompatActivity {

    private PessoaDAO dao = new PessoaDAO(this);

    private String valorRecebido;

    private Pessoa pessoa = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        try
        {
            this.getSupportActionBar().setElevation(0);
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            valorRecebido = extras.getString("userName");
        }

        ImageView imageView = (ImageView) findViewById(R.id.icon_profile_id);
        ImageView imageMaps = (ImageView) findViewById(R.id.ivMaps);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean userPassCheckResult = dao.checkUsername(valorRecebido);

                if(userPassCheckResult){
                    pessoa = dao.getDadosPessoa(valorRecebido);
                    Intent intentNewForm = new Intent(getApplicationContext(), FormNewUser.class);
                    intentNewForm.putExtra("DADO_PESSOA", pessoa);
                    startActivity(intentNewForm);
                } else{
                    Toast.makeText(MenuActivity.this, "Dado não encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        });


        imageMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMaps = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intentMaps);
            }
        });
    }
}