package com.example.shreeya.npl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by DELL 4U on 29-09-2015.
 */
public class SecondActivity extends Activity {
    Button ceaser;
    Button railfence;
    Button aes;
    Button share;
    String output1;
    String seedValue = "This Is MySecure";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);


        Button railfence = (Button) findViewById(R.id.bun3);

        Button share = (Button) findViewById(R.id.share);


        Intent intent = getIntent();
        final String msgcont = intent.getExtras().getString("text");

     /*   ceaser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Toast msg = Toast.makeText(getBaseContext(), "Encrypting magic happens", Toast.LENGTH_SHORT);
                msg.show();
                int n = msgcont.length();
                String upper = new String();
                String cipher = new String();
                upper = msgcont.toUpperCase();
                char pt[] = new char[n];
                for (int i = 0; i < n; i++) {
                    int asc = ((int) upper.charAt(i));
                    if (asc < 88) {
                        asc = asc + 3;

                    } else {
                        asc = asc + 3 - 26;
                    }
                    pt[i] = ((char) asc);
                    cipher = cipher + pt[i];

                }
                Toast msg1 = Toast.makeText(getBaseContext(), cipher, Toast.LENGTH_LONG);
                msg1.show();
                output1=cipher;


            }
        });*/
         railfence.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast msg = Toast.makeText(getBaseContext(), "Encrypting magic happens", Toast.LENGTH_SHORT);
                msg.show();

                String normalText = "VIJAY";
                String normalTextEnc = null;
                try {
                    normalTextEnc = AESHelper.encrypt(seedValue, msgcont);
                    String normalTextDec = AESHelper.decrypt(seedValue, normalTextEnc);


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Toast msg1 = Toast.makeText(getBaseContext(), normalTextEnc, Toast.LENGTH_LONG);
                msg1.show();
                output1=normalTextEnc;


            }
        });/*
         aes.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {

               Toast msg = Toast.makeText(getBaseContext(), "Encrypting magic happens", Toast.LENGTH_SHORT);
                 msg.show();

                 String input = msgcont;
                 String output = "";
                 int len = input.length(), flag = 0;

                 System.out.println("Input String : " + input);
                 for(int i=0;i<len;i+=2) {

                     output += input.charAt(i);
                 }
                 for(int i=1;i<len;i+=2) {

                     output += input.charAt(i);
                 }


                 Toast msg1 = Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG);
                msg1.show();
                 output1=output;


            }
        });
        m.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast msg = Toast.makeText(getBaseContext(), "Encrypting magic happens", Toast.LENGTH_SHORT);
                msg.show();

                String input = msgcont;
                String output=null;
                try {
                    output =M.doEncryption(msgcont);



                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Toast msg1 = Toast.makeText(getBaseContext(), output, Toast.LENGTH_LONG);
                msg1.show();
                output1=output;


            }
        });*/


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = output1;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));


            }
        });



    }

}
