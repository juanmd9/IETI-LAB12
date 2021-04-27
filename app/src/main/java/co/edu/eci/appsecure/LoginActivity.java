package co.edu.eci.appsecure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.edu.eci.appsecure.model.LoginWrapper;
import co.edu.eci.appsecure.model.Token;
import co.edu.eci.appsecure.service.AuthService;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void validarCampos(View view) {
        EditText editTextEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if(email==null || email.equals("")){
            editTextEmail.setError("Ingrese su email");
        } else if (password==null || password.equals("")){
            editTextPassword.setError("Ingrese su contraseña");
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://ieti-back.herokuapp.com/") //localhost for emulator
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AuthService authService = retrofit.create(AuthService.class);
            executorService.execute(new Runnable()
            {
                @Override
                public void run() {
                    Response<Token> response = null;
                    try {
                        response = authService.authenticate(new LoginWrapper(email, password)).execute();
                        System.out.println("res " + response.toString());

                        Token token = response.body();
                        if (token == null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    editTextEmail.setError("Credenciales invalidas!");
                                }
                            });
                        } else {
                            SharedPreferences sharedPref =
                                    getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("TOKEN_KEY", token.getAccessToken());
                            editor.commit();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                            finish();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }});
        }
    }

    public void logOut(View item) {
//        SharedPreferences sharedPref =
//                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.clear();
//        editor.commit();
//        R.id.l
        System.out.println("SALIO");
    }
}