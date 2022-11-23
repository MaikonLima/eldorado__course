package com.maik.org.lab_04;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.maik.org.lab_04.DAO.PessoaDAO;
import com.maik.org.lab_04.helper.FormularioHelper;
import com.maik.org.lab_04.model.Pessoa;

import java.io.File;

public class FormNewUser extends AppCompatActivity {
    private FormularioHelper helper;
    private Pessoa pessoaDado = null;
    private String localArquivo;
    private static final int FOTO_COD_CAMERA = 123;
    Uri localFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_new_user);

        try
        {
            this.getSupportActionBar().setElevation(0);
//            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            this.getSupportActionBar().setHomeButtonEnabled(true);
        }
        catch (NullPointerException e){}

        helper = new FormularioHelper(this);

        final LoadingDialog loadingDialog = new LoadingDialog(FormNewUser.this);

        pessoaDado = (Pessoa) getIntent().getSerializableExtra("DADO_PESSOA");

        if(pessoaDado != null){
            helper.setPessoa(pessoaDado);
        } else {
            Toast.makeText(this, "Não foi possível carregar dados", Toast.LENGTH_SHORT).show();
        }

        Button buttonCadastrar = (Button) findViewById(R.id.btCadastrar);
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pessoa pessoa = helper.pegaDadosFormulario();

                PessoaDAO dao = new PessoaDAO(FormNewUser.this);
                dao.cadastro(pessoa);
                dao.close();
                Toast.makeText(FormNewUser.this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentLogin);
                loadingDialog.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissDialog();
                    }
                }, 5000);
            }
        });

        helper.getFoto().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File caminhoArqFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                caminhoArqFile.mkdir();

                localArquivo = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + "/" + System.currentTimeMillis() + ".jpg";

                localArquivo.toString();

                File arquivo = new File(localArquivo);

                localFoto = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", arquivo);

                Intent irPraCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                irPraCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);

                startActivityForResult(irPraCamera, FOTO_COD_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FOTO_COD_CAMERA) {
            helper.carregaFoto(this.localArquivo);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                startActivity(new Intent(this, LoginActivity.class));
//                finishAffinity();
//                break;
//            default:break;
//        }
//        return true;
//    }
//
//    @Override
//    public void onBackPressed() {
//        startActivity(new Intent(this, MainActivity.class));
//        finishAffinity();
//        return;
//    }
}