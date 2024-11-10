package com.example.smartspend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class E extends AppCompatActivity {
    dbhelper2 db = new dbhelper2(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton ia, ii, is, iu, id;
        EditText ei, en, einfo, es, er;

        ia = findViewById(R.id.imageButton2); // Corrected button ID for "ia"
        ii = findViewById(R.id.imageButton20);
        is = findViewById(R.id.imageButton6);
        iu = findViewById(R.id.imageButton7);
        id = findViewById(R.id.imageButton8);
        ei = findViewById(R.id.editTextText);
        en = findViewById(R.id.editTextText2);
        einfo = findViewById(R.id.editTextDate2);
        es = findViewById(R.id.editTextText4);
        er = findViewById(R.id.editTextNumber);

        ia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(E.this, A.class);
                startActivity(intent);
            }
        });

        ii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(E.this, N.class);
                startActivity(intent);
            }
        });

        is.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int id = Integer.parseInt(ei.getText().toString());
                    String[] arr = db.showdata(id);
                    if (arr != null && arr.length == 6) {
                        en.setText(arr[1]);
                        einfo.setText(arr[2]);
                        er.setText(arr[3]);
                        es.setText(arr[4]);
                        int total = Integer.parseInt(arr[5]);
                    } 
                } catch (NumberFormatException e) {
                    Toast.makeText(E.this, "Enter the correct Value !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        
        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try {
                   String id =ei.getText().toString();
                   db.deleteData(id);
                   Toast.makeText(E.this, "Deleted Data !", Toast.LENGTH_SHORT).show();
                   en.setText("");
                   einfo.setText("");
                   er.setText("");
                   es.setText("");

               }
               catch (Exception e){
                   Toast.makeText(E.this, "Enter the carrect data !", Toast.LENGTH_SHORT).show();
               }
            }
        });

        iu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try {
                   int id = Integer.parseInt(ei.getText().toString());
                   String[] arr = db.showdata(id);
                   String name =en.getText().toString();
                   String info=einfo.getText().toString();
                   int Sprice=Integer.parseInt(es.getText().toString());
                   int Rprice=Integer.parseInt(er.getText().toString());
                   int pfinal=0;

                   // Check if both Sprice and Gprice are empty
                   if (es.getText().toString().isEmpty() && er.getText().toString().isEmpty()) {
                       Toast.makeText(E.this, "Sprice or Gprice, mention one!", Toast.LENGTH_SHORT).show();
                   } else {
                       // If Sprice is entered
                       if (!es.getText().toString().isEmpty()) {
                           Sprice = Integer.parseInt(es.getText().toString());
                           pfinal = Sprice-Rprice;  // Final price is set to Sprice
                       }

                       // If Gprice is entered
                       if (!er.getText().toString().isEmpty()) {
                           Rprice = Integer.parseInt(er.getText().toString());
                           pfinal =Sprice-Rprice;  // Final price is set to Gprice if it was entered
                       }
                       db.updateRecordById(id,name,info,Rprice,Sprice,pfinal);
                       Toast.makeText(E.this, "Data updated !", Toast.LENGTH_SHORT).show();
                       ei.setText("");
                   }
               }
               catch (Exception e){
                   Toast.makeText(E.this, "Enter valid data !", Toast.LENGTH_SHORT).show();
               }
            }
        });

    }
}
