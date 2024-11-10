package com.example.smartspend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class maintableinsertdata extends AppCompatActivity {
    dbhelper db = new dbhelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintableinsertdata);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageButton bh,ba;
        Button b ;
        EditText e1, e2, e3, e4, e5;
        b = findViewById(R.id.button);
        bh = findViewById(R.id.imageButton);
        ba = findViewById(R.id.imageButton4);
        e1 = findViewById(R.id.editTextText);
        e2 = findViewById(R.id.editTextText2);
        e3 = findViewById(R.id.editTextDate2);
        e4 = findViewById(R.id.editTextText4);
        e5 = findViewById(R.id.editTextNumber);
        bh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(maintableinsertdata.this, MainActivity.class);
                startActivity(i);
            }
        });

        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(maintableinsertdata.this, AllData.class);
                startActivity(n);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = e1.getText().toString();
                String info = e2.getText().toString();
                String date = e3.getText().toString();
                String other = e4.getText().toString();
                int price = 0;

                // Check if price is empty or invalid
                try {
                    price = Integer.parseInt(e5.getText().toString());
                } catch (NumberFormatException ex) {
                    price = 0; // In case the price is not a valid number
                }

                // Check for empty name or invalid price (0 or below)
                if (name.isEmpty() || price <= 0) {
                    Toast.makeText(maintableinsertdata.this, "Enter the name and a valid price!", Toast.LENGTH_SHORT).show();
                } else {
                    // Calculate total
                    int total = db.getPreviousTotal() + price; // Adjust if '5' is dynamic or should come from a different calculation
                    db.insertdata(name, info, date, other, price, total);

                    // Show success message
                    Toast.makeText(maintableinsertdata.this, "Data inserted successfully.", Toast.LENGTH_SHORT).show();

                    // Clear all fields
                    e1.setText("");
                    e2.setText("");
                    e3.setText("");
                    e4.setText("");
                    e5.setText("");
                }
            }
        });

    }
}