package com.example.smartspend;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditDatabase extends AppCompatActivity {
    TextToSpeech text;
    dbhelper db =new dbhelper(this);
    dbhelper2 db2=new dbhelper2(this);
    int ch=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_database);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        text =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                text.setLanguage(Locale.UK);
            }
        });


       Button b ,bd,b1,bd1;
        ImageButton i ,i1;
        b=findViewById(R.id.button2);
        bd=findViewById(R.id.button3);
        b1=findViewById(R.id.button4);
        bd1=findViewById(R.id.button5);
        i=findViewById(R.id.imageButton12);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    text.speak("Save the pdf or you can share the pdf file",TextToSpeech.QUEUE_FLUSH,null);
                    createPdfFromText(db.displayAllData());
                }
                catch (Exception e){
                    Toast.makeText(EditDatabase.this, "error the opration !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              try {
                  if(ch==0 || ch==1){
                      Toast.makeText(EditDatabase.this, "Please make sure you have deleted your database !", Toast.LENGTH_SHORT).show();
                      text.speak("Please make sure you have deleted your database !",TextToSpeech.QUEUE_FLUSH,null);
                      ch++;
                  }
                  else{
                      db.truncateMainTable();
                      Toast.makeText(EditDatabase.this, "Delete all data ! ", Toast.LENGTH_SHORT).show();
                      text.speak("Delete all data ! ",TextToSpeech.QUEUE_FLUSH,null);

                  }
              }
              catch (Exception e){
                  Toast.makeText(EditDatabase.this, "opration dead !", Toast.LENGTH_SHORT).show();
              }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    text.speak("Save the pdf or you can share the pdf file",TextToSpeech.QUEUE_FLUSH,null);
                    createPdfFromText(db2.displayAllData());
                }
                catch (Exception e){
                    Toast.makeText(EditDatabase.this, "error the opration !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(ch==0 || ch==1){
                        Toast.makeText(EditDatabase.this, "Please make sure you have deleted your database !", Toast.LENGTH_SHORT).show();
                        text.speak("Please make sure you have deleted your database !",TextToSpeech.QUEUE_FLUSH,null);
                        ch++;
                    }
                    else{
                        db2.truncateMainTable();
                        Toast.makeText(EditDatabase.this, "Delete all data ! ", Toast.LENGTH_SHORT).show();
                        text.speak("Delete all data ! ",TextToSpeech.QUEUE_FLUSH,null);

                    }
                }
                catch (Exception e){
                    Toast.makeText(EditDatabase.this, "opration dead !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditDatabase.this, MainActivity.class);

                startActivity(intent);
            }
        });
    }


    public void createPdfFromText(String text) {
        Toast.makeText(this, "Save the pdf or you can share the pdf file ", Toast.LENGTH_SHORT).show();

        // Define page width and height (A4 size in points: 595x842)
        int pageWidth = 595;
        int pageHeight = 842;

        // Create a new PdfDocument
        PdfDocument pdfDocument = new PdfDocument();

        // Create a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();

        // Start a page
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        // Prepare Canvas to draw text
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setTextSize(12);

        int x = 40;
        int y = 50;
        int lineSpacing = 20; // Adjust line spacing

        // Draw text onto the page
        for (String line : text.split("\n")) {
            canvas.drawText(line, x, y, paint);
            y += lineSpacing;
            if (y > pageHeight - 50) {  // Check if the text exceeds the page height
                pdfDocument.finishPage(page);
                y = 50;  // Reset y for new page
                page = pdfDocument.startPage(pageInfo);
                canvas = page.getCanvas();
            }
        }

        // Finish the current page
        pdfDocument.finishPage(page);

        // Write the document content to internal storage
        File file = new File(getFilesDir(), "output.pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the document
            pdfDocument.close();
        }

        // Share or open the PDF
        Uri pdfUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_STREAM, pdfUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Start a chooser for sharing or opening the PDF
        startActivity(Intent.createChooser(intent, "Share PDF"));
    }


}