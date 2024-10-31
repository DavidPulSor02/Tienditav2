package com.example.tienditav2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegisterHere;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        // Enlazar vistas del layout con el código
        etEmail = findViewById(R.id.etEmailOrUser);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegisterHere = findViewById(R.id.tvRegisterHere); // Referencia al TextView de registro

        // Acción para el botón de iniciar sesión
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Verificar si el email y contraseña son correctos
                if (loginIsValid(email, password)) {
                    // Guardar el correo del usuario en SharedPreferences
                    SharedPreferences sharedPref = getSharedPreferences("userSession", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("email", email);
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    // Redirigir a MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Mostrar un mensaje de error si el login no es válido
                    Toast.makeText(LoginActivity.this, "Email o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Acción para el TextView de registrarse
            tvRegisterHere.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Redirigir a RegistroActivity cuando se haga clic
                    Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                    startActivity(intent);
                }
            });
        }

    // Función básica de validación de credenciales
    private boolean loginIsValid(String email, String password) {
        // Aquí deberías implementar la lógica real de autenticación,
        // por ejemplo, consultando una base de datos o un servicio web.
        // Este es solo un ejemplo básico:
        return email.equals("vichopulidosorcia433@gmail.com") && password.equals("12345678");
    }
}
