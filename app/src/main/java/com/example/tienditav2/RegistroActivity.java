package com.example.tienditav2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistroActivity extends AppCompatActivity {

    private EditText etNombre, etEmail, etPassword;
    private Button btnRegistrar;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registro);


        etNombre = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        tvLogin = findViewById(R.id.tvLogin);

        // Acción del botón de registro
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los campos
                String nombre = etNombre.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Validar que los campos no estén vacíos
                if (TextUtils.isEmpty(nombre)) {
                    Toast.makeText(RegistroActivity.this, "Ingresa un nombre", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegistroActivity.this, "Ingresa un email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegistroActivity.this, "Ingresa una contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Guardar la información del usuario
                guardarUsuario(nombre, email, password);

                // Guardar el estado de sesión activa
                guardarSesionActiva(email);

                // Redirigir a la pantalla principal de la app
                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finalizar la actividad de registro
            }
        });

        // Acción para el TextView de login
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a LoginActivity
                Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finalizar la actividad de registro
            }
        });
    }

    // Guardar el usuario como un objeto JSON
    private void guardarUsuario(String nombre, String email, String password) {
        // Obtener los datos actuales almacenados
        SharedPreferences sharedPref = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        // Guardar el usuario como un objeto en formato JSON
        String usuariosRegistrados = sharedPref.getString("usuarios", "{}");

        try {
            JSONObject usuarios = new JSONObject(usuariosRegistrados);
            JSONObject nuevoUsuario = new JSONObject();
            nuevoUsuario.put("nombre", nombre);
            nuevoUsuario.put("password", password); // En apps reales usa encriptación para las contraseñas

            usuarios.put(email, nuevoUsuario);

            editor.putString("usuarios", usuarios.toString());
            editor.apply();

            Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
        }
    }

    // Guardar el estado de sesión activa
    private void guardarSesionActiva(String email) {
        SharedPreferences sharedPref = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("usuarioActivo", email); // Guardamos el email como indicador del usuario activo
        editor.apply();
    }
}
