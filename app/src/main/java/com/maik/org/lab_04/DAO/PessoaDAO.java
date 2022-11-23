package com.maik.org.lab_04.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.maik.org.lab_04.model.Pessoa;

public class PessoaDAO extends SQLiteOpenHelper {
    private static final int VERSAO = 1;
    private static final String TABELA = "PESSOA";
    private static final String DATA_BASE = "AppMotoAcademyBD";
    private static final String TAG = "APP_PESSOA";

    public PessoaDAO(Context context) {
        super(context, DATA_BASE, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + "( id INTEGER PRIMARY KEY, " +
                "nome TEXT, telefone TEXT, endereco TEXT, " +
                "login TEXT UNIQUE NOT NULL, senha TEXT, foto TEXT, site TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String sql = "DROP TABLE IF EXISTS " + TABELA;

        db.execSQL(sql);

        onCreate(db);

    }

    //POST_USUARIO
    public void cadastro (Pessoa pessoaobj) {
        ContentValues values = new ContentValues();
        values.put("nome", pessoaobj.getNome());
        values.put("telefone", pessoaobj.getTelefone());
        values.put("endereco", pessoaobj.getEndereco());
        values.put("login", pessoaobj.getLogin());
        values.put("senha", pessoaobj.getSenha());
        values.put("site", pessoaobj.getSite());
        values.put("foto", pessoaobj.getFoto());

        getWritableDatabase().insert(TABELA, null, values);
    }

    //UPDATE_USUARIO
    public void updateCadastro (Pessoa pessoaobj) {
        ContentValues values = new ContentValues();
        values.put("nome", pessoaobj.getNome());
        values.put("telefone", pessoaobj.getTelefone());
        values.put("endereco", pessoaobj.getEndereco());
        values.put("login", pessoaobj.getLogin());
        values.put("senha", pessoaobj.getSenha());
        values.put("site", pessoaobj.getSite());
        values.put("foto", pessoaobj.getFoto());

        String[] args = {pessoaobj.getId().toString()};

        getWritableDatabase().update(TABELA, values, "id=?", args);

    }

    //DELETE_USUARIO
    public void deletUsuario () {}



    // funcao que verifica se o usuario existe na base de dados
    public Boolean checkUsername(String username)
    {
        SQLiteDatabase myDB = getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from "+TABELA+"  where login = ?", new String[] {username});

        if (cursor.getCount() > 0)
        {
            return true;  // usuario existe
        }
        else
        {
            return  false;
        }
    }

    public Boolean checkUsernamePassword (String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();

        Cursor cursor = myDB.rawQuery("SELECT * FROM "+TABELA+" WHERE login = ? AND senha = ?",
                new String[] {username, password});

        if (cursor.getCount() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Pessoa getDadosPessoa(String username){
        SQLiteDatabase myDB = getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from "+TABELA+"  where login = ?", new String[] {username}); // if the data exists the cursor will have some data
        Pessoa pessoa2 = new Pessoa();

        if(cursor.moveToFirst()){
            pessoa2.setId(cursor.getLong(0));
            pessoa2.setNome(cursor.getString(1));
            pessoa2.setTelefone(cursor.getString(2));
            pessoa2.setEndereco(cursor.getString(3));
            pessoa2.setLogin(cursor.getString(4));
            pessoa2.setSenha(cursor.getString(5));
            pessoa2.setFoto(cursor.getString(6));
            pessoa2.setSite(cursor.getString(7));
        } else {
            cursor.close();
        }

        return pessoa2;
    }

}
