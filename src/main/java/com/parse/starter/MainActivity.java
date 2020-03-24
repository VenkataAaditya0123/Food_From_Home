/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

//password : ulQNL2c6mHAd

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnKeyListener{

  TextView loginTextView;
  EditText usernameEditText;
  EditText passwordEditText;
  Button signUpButton;
  Boolean signUpMode = false;

  @Override
  public boolean onKey(View view, int i, KeyEvent keyEvent) {

    if(i == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == keyEvent.ACTION_DOWN)
    {
      signUpClicked(view);
    }

    return false;
  }

  @Override
  public void onClick(View view) {
    if(view.getId()== R.id.loginTextView)
    {
      if(signUpMode)
      {
        signUpMode = false;
        signUpButton.setText("Login");
        loginTextView.setText("Or,Sign Up.");
      }
      else
      {
        signUpMode = true;
        signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setText("Sign Up");
        loginTextView.setText("Or,Login.");

      }
    }
    else if(view.getId() == R.id.logoImageView || view.getId() == R.id.backgroundLayout)
    {
      InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }
  }

  public void signUpClicked(View view){



    if(usernameEditText.getText().toString().equals("") || passwordEditText.getText().toString().equals(""))
    {
      Toast.makeText(this,"A username and password are required.",Toast.LENGTH_LONG).show();
    }
    else
    {
      if(signUpMode)
      {
        ParseUser user  = new ParseUser();
        user.setUsername(usernameEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
            if(e == null)
            {
              Log.d("sign up","Success!");
              Toast.makeText(MainActivity.this,"Congrats you are now Signed Up!",Toast.LENGTH_LONG).show();
            }
            else
            {
              Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
          }
        });
      }
      else{

        ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
            if(user!=null && e==null)
            {
              Toast.makeText(MainActivity.this,"Welcome "+ usernameEditText.getText().toString(),Toast.LENGTH_LONG).show();
            }
            else
            {
              Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
          }
        });
      }

    }
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

     usernameEditText =  findViewById(R.id.usernameEditText);
     passwordEditText =  findViewById(R.id.passwordEditText);

     loginTextView = findViewById(R.id.loginTextView);
     loginTextView.setOnClickListener(this);

    ImageView logoImageView = findViewById(R.id.logoImageView);
    RelativeLayout backgroungLayout = findViewById(R.id.backgroundLayout);

    logoImageView.setOnClickListener(this);
    backgroungLayout.setOnClickListener(this);




    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}


