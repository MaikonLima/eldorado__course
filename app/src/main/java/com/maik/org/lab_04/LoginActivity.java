package com.maik.org.lab_04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maik.org.lab_04.DAO.PessoaDAO;

public class LoginActivity extends AppCompatActivity {

    private PessoaDAO dao = new PessoaDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_actvity);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        final LoadingDialog loadingDialog = new LoadingDialog(LoginActivity.this);

        EditText login = (EditText) findViewById(R.id.etSIGNIN);
        EditText senha = (EditText) findViewById(R.id.etPASSWORD);
        Button btLogin = (Button) findViewById(R.id.btLOGIN);

        btLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String user = login.getText().toString().trim();
                    String pass = senha.getText().toString().trim();

                    if(user.equals("") || pass.equals("")) {
                        Toast.makeText(LoginActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    } else {

                        Boolean userPassCheckResult = dao.checkUsernamePassword(user, pass);

                        if(userPassCheckResult) {
                            Intent intentNext = new Intent(LoginActivity.this, MenuActivity.class);
                            intentNext.putExtra("userName", user);
                            startActivity(intentNext);
                            loadingDialog.startLoadingDialog();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                  loadingDialog.dismissDialog();
                              }
                            }, 5000);
                     } else {
                            Toast.makeText(LoginActivity.this, "Credencial inválida", Toast.LENGTH_SHORT).show();
                       }
                    }
                }
            });

        TextView newAccount = (TextView) findViewById(R.id.tvNovaConta);
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(getApplicationContext(), FormNewUser.class);
                startActivity(newIntent);
            }
        });


        TextView tvForgottPassword = (TextView) findViewById(R.id.tvForgottPassword);

        tvForgottPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentObj = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(intentObj);
            }
        });
    }
}