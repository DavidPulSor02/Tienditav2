package com.example.tienditav2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 2000; // 3 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Simular una carga de 3 segundos y luego iniciar la actividad principal
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Comprobar si el usuario está autenticado, por ejemplo
                boolean isLoggedIn = checkUserSession();

                if (isLoggedIn) {
                    // Si el usuario está logueado, ir al MainActivity
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Si no está logueado, ir al LoginActivity
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish(); // Finalizar SplashActivity para que no se pueda regresar a ella
            }
        }, SPLASH_TIME_OUT);
    }

    // Método para simular la verificación de si el usuario está logueado
    private boolean checkUserSession() {
        // Aquí puedes agregar la lógica para verificar si el usuario está autenticado,
        // por ejemplo, usando SharedPreferences o FirebaseAuth.
        return false; // Aquí puedes cambiar la lógica para tu app
    }
}
