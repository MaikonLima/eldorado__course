package com.maik.org.lab_04.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.ImageView;

import com.maik.org.lab_04.FormNewUser;
import com.maik.org.lab_04.R;
import com.maik.org.lab_04.model.Pessoa;

import java.io.IOException;

public class FormularioHelper {
    private EditText nome;
    private EditText telefone;
    private EditText endereco;
    private EditText login;
    private EditText senha;
    private EditText site;
    private ImageView foto;

    Pessoa pessoaobj;

    public ImageView getFoto() {
        return foto;
    }

    public FormularioHelper (FormNewUser activityObj) {
        pessoaobj = new Pessoa();

        this.nome = (EditText) activityObj.findViewById(R.id.etLOGIN);
        this.telefone = (EditText) activityObj.findViewById(R.id.etPhone);
        this.endereco = (EditText) activityObj.findViewById(R.id.etCEP);
        this.login = (EditText) activityObj.findViewById(R.id.etLogin);
        this.senha = (EditText) activityObj.findViewById(R.id.etSENHA);
        this.site = (EditText) activityObj.findViewById(R.id.etSite);
        this.foto = (ImageView) activityObj.findViewById(R.id.ivAvatar);
    }

    public Pessoa pegaDadosFormulario () {
        pessoaobj.setNome(nome.getEditableText().toString());
        pessoaobj.setTelefone(telefone.getEditableText().toString());
        pessoaobj.setEndereco(endereco.getEditableText().toString());
        pessoaobj.setLogin(login.getEditableText().toString());
        pessoaobj.setSenha(senha.getEditableText().toString());
        pessoaobj.setSite(site.getEditableText().toString());
        pessoaobj.setFoto(foto.toString());

        return pessoaobj;
    }

    public void carregaFoto(String localFoto){
        Bitmap imagemFoto = BitmapFactory.decodeFile(localFoto);

        Bitmap fotoProfile = Bitmap.createScaledBitmap(imagemFoto, 100, 100, true);

        pessoaobj.setFoto(localFoto);

        foto.setImageBitmap(fotoProfile);
    }

    public void setPessoa(Pessoa pessoa) {
        nome.setText(pessoa.getNome());
        telefone.setText(pessoa.getTelefone());
        endereco.setText(pessoa.getEndereco());
        login.setText(pessoa.getLogin());
        senha.setText(pessoa.getSenha());
        site.setText(pessoa.getSite());

        this.pessoaobj = pessoa;

//        if(pessoa.getFoto() != null) {
//            carregaFoto(pessoa.getFoto());
//        }
    }
}
