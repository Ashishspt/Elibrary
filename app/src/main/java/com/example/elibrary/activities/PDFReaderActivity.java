package com.example.elibrary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elibrary.R;
import com.example.elibrary.helpers.AppActivity;
import com.example.elibrary.helpers.ShowToast;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class PDFReaderActivity extends AppActivity implements View.OnClickListener {
    private PDFView pdfView;
    private TextView pdfbookname;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfreader);
        initializedView();
        initializedListners();
    }

    @Override
    protected void initializedView() {
        pdfView=findViewById(R.id.pdfview);
        pdfbookname=findViewById(R.id.book_name_pdf);
        back=findViewById(R.id.back_arrow);


    }

    @Override
    protected void initializedListners() {



        String string=getIntent().getExtras().getString("bookuri");
        String bookname=getIntent().getExtras().getString("bookname");

        pdfbookname.setText(bookname);


        ShowToast.withLongMessage("pdf Loading");
        new RetrievePDF().execute(string);
        back.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_arrow:
                PDFReaderActivity.this.finish();
                break;

        }
    }

    class RetrievePDF extends AsyncTask<String,Void,InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream=null;
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                if(httpURLConnection.getResponseCode()==200){
                    inputStream=new BufferedInputStream(httpURLConnection.getInputStream());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
        }
    }

}
