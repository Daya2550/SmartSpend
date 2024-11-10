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

public class satting extends AppCompatActivity {
dbhelper db=new dbhelper(this);
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_satting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageButton bh,ba,bf,bd,bu;
        EditText ei,en,einfo,ed,ep;
        bh=findViewById(R.id.imageButton2);
        ba=findViewById(R.id.imageButton5);
        bf=findViewById(R.id.imageButton6);
        bd=findViewById(R.id.imageButton8);
        bu=findViewById(R.id.imageButton7);
        ei=findViewById(R.id.editTextText);
        en=findViewById(R.id.editTextText2);
        einfo=findViewById(R.id.editTextDate2);
        ed=findViewById(R.id.editTextText4);
        ep=findViewById(R.id.editTextNumber);
       bf.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              try {
                  String arr[]=new String[7];
                  arr=db.showdata(Integer.parseInt(ei.getText().toString()));
                  en.setText(arr[1]);
                  einfo.setText(arr[2]);
                  ed.setText(arr[3]);
                  ep.setText(arr[5]);
              }
              catch (Exception e){
                  Toast.makeText(satting.this, "ID wrong Enterd !", Toast.LENGTH_SHORT).show();
                  ei.setText("");
              }
           }
       });

       bd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               try {
                   String arr[]=new String[7];
                   arr=db.showdata(Integer.parseInt(ei.getText().toString()));
                   int total =db.getPreviousTotal();
                   total=total-Integer.parseInt(arr[5]);
                   db.updateLastRecordTotal(total);
                   db.deleteData(ei.getText().toString());
                   Toast.makeText(satting.this, "Deleted !", Toast.LENGTH_SHORT).show();
               }
               catch (Exception e){
                   Toast.makeText(satting.this, "Data Not Found !", Toast.LENGTH_SHORT).show();
                   ei.setText("");
               }
           }
       });

       bu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              try {
                  String arr[]=new String[7];
                  arr=db.showdata(Integer.parseInt(ei.getText().toString()));
                  int oprice=Integer.parseInt(arr[5]);
                  int nprice =Integer.parseInt(ep.getText().toString());
                  int newp=nprice-oprice;
                  int mtotal =db.getPreviousTotal();
                  mtotal=mtotal+newp;
                  int id =Integer.parseInt(ei.getText().toString());
                  String name=en.getText().toString();
                  String info=einfo.getText().toString();
                  String date=ed.getText().toString();
                  int price=Integer.parseInt(ep.getText().toString());
                  db.updateRecordById(id,name,info,date,price);
                  db.updateLastRecordTotal(mtotal);
                  Toast.makeText(satting.this, "Updated", Toast.LENGTH_SHORT).show();
              }
              catch (Exception e){
                  Toast.makeText(satting.this, "Wrong data inserted !", Toast.LENGTH_SHORT).show();
                  ei.setText("");
              }
           }
       });

       bh.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(satting.this, MainActivity.class);
               startActivity(intent);
           }
       });

       ba.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(satting.this, AllData.class);
               startActivity(intent);
           }
       });
    }
}