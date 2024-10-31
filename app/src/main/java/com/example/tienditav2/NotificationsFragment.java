package com.example.tienditav2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tienditav2.ui.notifications.CheckoutActivity;

import java.util.ArrayList;

class CartActivity extends AppCompatActivity {
    ListView cartListView;
    Button checkoutButton;
    ArrayList<String> cartItems; // Lista para almacenar los elementos del carrito

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notifications); // Asegúrate de que este sea el nombre correcto de tu archivo XML

        // Inicializar vistas
        cartListView = findViewById(R.id.cart_list);
        checkoutButton = findViewById(R.id.checkout);

        // Obtener los elementos del carrito pasados desde MainActivity
        cartItems = getIntent().getStringArrayListExtra("cart");

        // Configurar el adaptador para la ListView
        ArrayAdapter<String> cartAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cartItems);
        cartListView.setAdapter(cartAdapter);

        // Manejar clic en el botón de finalizar pedido
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes agregar la lógica para finalizar el pedido
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class); // Asegúrate de crear esta actividad
                startActivity(intent);
            }
        });
    }
}
