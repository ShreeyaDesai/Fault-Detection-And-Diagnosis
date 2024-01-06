package com.example.shreeya.reciever;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button ceaser;
    Button railfence;
    Button aes;
    EditText eText;
    String seedValue = "This Is MySecure";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eText=(EditText) findViewById(R.id.editText);


        Button railfence = (Button) findViewById(R.id.button2);


        /* ceaser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str=eText.getText().toString();
                Toast msg = Toast.makeText(getBaseContext(), "Encrypting magic happens", Toast.LENGTH_SHORT);
                msg.show();

                int n=str.length();

                String upper = new String();
                String cipher = new String();
                upper = str.toUpperCase();
                char pt[] = new char[n];
                for (int i = 0; i < n; i++) {
                    int asc = ((int) upper.charAt(i));
                    if (asc < 88) {
                        asc = asc -3;

                    } else {
                        asc = asc + 3 - 26;
                    }
                    pt[i] = ((char) asc);
                    cipher = cipher + pt[i];

                }
                Toast msg1 = Toast.makeText(getBaseContext(), cipher, Toast.LENGTH_LONG);
                msg1.show();


            }
        });*/
      railfence.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = eText.getText().toString();


                String normalTextDec = null;
                try {

                    normalTextDec = AESHelper.decrypt(seedValue, str);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Toast msg1 = Toast.makeText(getBaseContext(), normalTextDec, Toast.LENGTH_LONG);
                msg1.show();


            }
        });/*
        aes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = eText.getText().toString();


                String input = str;
                String output = "";
                int len = input.length(), flag = 0;

                System.out.println("Input String : " + input);
                for (int i = 0; i < len; i += 2) {

                    output += input.charAt(i+2);
                }
                for (int i = 1; i < len; i += 2) {

                    output += input.charAt(i+2);
                }


                Toast msg1 = Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG);
                msg1.show();



            }
        });
        m.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str=eText.getText().toString();

                String input = str;
                String output = null;
                try {
                    output = M.doDecryption(str);


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Toast msg1 = Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG);
                msg1.show();


            }
        });*/
}}
