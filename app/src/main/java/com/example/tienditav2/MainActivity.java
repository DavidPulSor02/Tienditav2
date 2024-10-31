package com.example.tienditav2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tienditav2.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    private ListView productList;
    private ArrayAdapter<String> productAdapter;
    private ArrayList<String> products;
    private Queue<String> cart;  // Usamos una cola para almacenar los pedidos en orden de llegada
    private Button viewCartButton;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificar si hay un usuario activo antes de cargar la vista principal
        SharedPreferences sharedPref = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        String usuarioActivo = sharedPref.getString("usuarioActivo", null);

        if (usuarioActivo == null) {
            // No hay usuario logueado, redirigir a LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Finalizar la actividad para evitar que se pueda volver atrás
            return; // Salir del método onCreate para evitar cargar la interfaz
        }

        // Si el usuario está logueado, cargar la vista principal
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productList = findViewById(R.id.product_list);
        viewCartButton = findViewById(R.id.view_cart);

        products = new ArrayList<>();
        cart = new LinkedList<>();  // Inicializamos la cola

        // Agregamos productos a la lista
        products.add("Café Americano");
        products.add("Capuchino");
        products.add("Latte");
        products.add("Té Chai");
        products.add("Sandwich");

        // Configuramos el adaptador
        productAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, products);
        productList.setAdapter(productAdapter);

        // Manejar clicks en los elementos de la lista de productos
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = products.get(position);
                cart.add(selectedItem);  // Agregamos el pedido a la cola
            }
        });

        // Manejar clic en el botón de ver carrito
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                intent.putStringArrayListExtra("cart", new ArrayList<>(cart)); // Pasamos la cola convertida en ArrayList
                startActivity(intent);
            }
        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Configurar la barra de navegación
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    // Añade este método en MainActivity para cerrar sesión
    private void logout() {
        SharedPreferences sharedPref = getSharedPreferences("userSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear(); // Elimina todos los datos de la sesión
        editor.apply();

        // Redirigir a LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
