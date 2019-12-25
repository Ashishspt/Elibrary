package com.example.elibrary.helpers;

import androidx.appcompat.app.AppCompatActivity;

public abstract class AppActivity extends AppCompatActivity {
    abstract protected void initializedView();
    abstract protected void initializedListners();


}
