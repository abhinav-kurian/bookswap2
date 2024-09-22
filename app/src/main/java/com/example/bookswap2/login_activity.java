package com.example.bookswap2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//import the following
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

public class login_activity extends AppCompatActivity {
    //declarations
    private EditText edit_uname,edit_pwd;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //initialize widgets
        edit_uname=findViewById(R.id.NAME_TEXT);
        edit_pwd=findViewById(R.id.PASSWORD_TEXT);
        //create an object for DBHandler class
        dbHandler=new DBHandler(login_activity.this);
    }

    //onclick method for Login Button
    public void loginCheck(View v)
    {
        //retrieve the values from edittext fields
        String username=edit_uname.getText().toString();
        String password=edit_pwd.getText().toString();
        //validate textfields
        if(username.equals("") || password.equals(""))
        {
            Toast.makeText(login_activity.this,"All fields are manadatory",Toast.LENGTH_LONG).show();
        }
        else {
            //call checkEmailPassword method of DBHandler Class, returns true on successful login
            Boolean check= dbHandler.checkEmailPassword(username, password);
            //clear textfields
            edit_uname.setText("");
            edit_pwd.setText("");

            if(check == true) {
                Toast.makeText(login_activity.this,"Successful Login",Toast.LENGTH_LONG).show();
                //displays SuccessLoginActivity on successful login
                Intent i = new Intent(login_activity.this, HomePageActivity.class);
                //pass the username to  SuccessLoginActivity to display, as key-value pair
                i.putExtra("USERNAME",username);
                startActivity(i);
            }
            else
            {
                Toast.makeText(login_activity.this,"Invalid Credentials",Toast.LENGTH_LONG).show();
            }
        }
    }
}