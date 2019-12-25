package com.example.elibrary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.elibrary.R;
import com.example.elibrary.helpers.AppActivity;
import com.example.elibrary.helpers.ShowToast;
import com.example.elibrary.models.Login;
import com.example.elibrary.presenters.LoginPresenter;
import com.example.elibrary.utilis.Utilities;
import com.example.elibrary.utilis.UtilitiesFunctions;

public class LoginActivity extends AppActivity implements View.OnClickListener, LoginPresenter.View {
    private EditText email;
    private EditText password;
    private Button btn_login;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializedView();
        initializedListners();
        checkIfUserIsLogin();
    }

    private void checkIfUserIsLogin() {
        if(Utilities.isLogin())
        {
            afterLoginSuccess();
        }
    }

    @Override
    protected void initializedView() {
        email=findViewById(R.id.get_username);
        password=findViewById(R.id.get_password);
        btn_login=findViewById(R.id.btn_login);



    }

    @Override
    protected void initializedListners() {
        loginPresenter=new LoginPresenter(this);
        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                doLogin(email,password);

                break;
        }
    }

    @Override
    public void onLoginResponseSuccess(Login login) {
        afterLoginSuccess();



    }

    @Override
    public void onLoginResponseFailure(String message) {
        ShowToast.withMessage("failure");

    }

    private void afterLoginSuccess(){
        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        LoginActivity.this.finish();
    }

private void doLogin(EditText email,EditText password){
    if (email.getText().toString().isEmpty() && password.getText().toString().isEmpty()) {
        Toast.makeText(this, "All Filed ARE Mandatoru", Toast.LENGTH_SHORT).show();
    }
    else {
        if (Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            if(UtilitiesFunctions.isNetworkAvailable(LoginActivity.this)){

                loginPresenter.userLogin(email.getText().toString(), password.getText().toString());
            }
            else{
                ShowToast.withMessage("Please Connect to Internet");
            }

        } else {
            ShowToast.withMessage("Valid email is required");
        }

    }
}
}
