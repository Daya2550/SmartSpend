package com.example.smartspend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class N extends AppCompatActivity {
    dbhelper2 db =new dbhelper2(this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_n);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageButton i,i1;
        Button b;
        EditText tn,ti,ts,tr;
        i=findViewById(R.id.imageButton);
        i1=findViewById(R.id.imageButton19);
        b=findViewById(R.id.button);
        tn=findViewById(R.id.editTextText);
        ti=findViewById(R.id.editTextText2);
        ts=findViewById(R.id.editTextText4);
        tr=findViewById(R.id.editTextNumber);

        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(N.this, A.class);
                startActivity(i);
            }
        });

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(N.this, E.class);
                startActivity(i);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try {
                   String name = tn.getText().toString();
                   String info = ti.getText().toString();
                   int Sprice = 0;
                   int Rprice = 0;
                   int pfinal = 0;

                   // Check if both Sprice and Gprice are empty
                   if (ts.getText().toString().isEmpty() && tr.getText().toString().isEmpty()) {
                       Toast.makeText(N.this, "Sprice or Gprice, mention one!", Toast.LENGTH_SHORT).show();
                   } else {
                       // If Sprice is entered
                       if (!ts.getText().toString().isEmpty()) {
                           Sprice = Integer.parseInt(ts.getText().toString());
                           pfinal = Sprice-Rprice;  // Final price is set to Sprice

                       }

                       // If Gprice is entered
                       if (!tr.getText().toString().isEmpty()) {
                           Rprice = Integer.parseInt(tr.getText().toString());
                           pfinal =Sprice-Rprice;  // Final price is set to Gprice if it was entered

                       }

                       db.insertdata(name, info,Rprice, Sprice, pfinal);
                       Toast.makeText(N.this, "Data inserted !", Toast.LENGTH_SHORT).show();

                   }
               }catch (Exception e){
                   Toast.makeText(N.this, "Error !", Toast.LENGTH_SHORT).show();
               }
            }
        });

    }
}