package com.example.elibrary.activities;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.elibrary.R;
import com.example.elibrary.apiservices.BookDetailsApiService;
import com.example.elibrary.helpers.AppActivity;
import com.example.elibrary.helpers.AppRecyclerViewAdapter;
import com.example.elibrary.helpers.DefineClassType;
import com.example.elibrary.helpers.MyApplication;
import com.example.elibrary.helpers.ShowToast;
import com.example.elibrary.models.BookDetails;
import com.example.elibrary.presenters.BookDetailsPresenter;
import com.example.elibrary.utilis.Utilities;
import com.example.elibrary.utilis.UtilitiesFunctions;
import com.google.gson.GsonBuilder;

import java.util.List;

public class BookDetailsActivity extends AppActivity implements BookDetailsPresenter.View, View.OnClickListener {
    private RecyclerView recyclerView;
    private BookDetailsRecyclerViewAdapter bookDetailsRecyclerViewAdapter;
    private BookDetailsPresenter bookDetailsPresenter;
    private BookDetails bookdata;
    private ImageView backarrow;
    private  String bookID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bookID = extras.getString("bookid");
        }
        initializedView();
        initializedListners();
        prepareRecyclerView();
    }



    @Override
    protected void initializedView() {

        recyclerView=findViewById(R.id.details_recycler_view);
        backarrow=findViewById(R.id.back_arrow);
        backarrow.setOnClickListener(this);
    }

    @Override
    protected void initializedListners() {
        bookDetailsPresenter=new BookDetailsPresenter(this);

        bookDetailsPresenter.bookDetails(String.valueOf(bookID));






    }

    private void prepareRecyclerView() {
        bookDetailsRecyclerViewAdapter=new BookDetailsRecyclerViewAdapter();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(BookDetailsActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(bookDetailsRecyclerViewAdapter);
    }

    @Override
    public void onBookDetailsResponseSuccess(BookDetails bookDetails) {
        Log.e("onBookseSuccess: ",new GsonBuilder().create().toJson(bookDetails));
        bookDetailsRecyclerViewAdapter.add(bookDetails);
        bookDetailsRecyclerViewAdapter.notifyDataSetChanged();





    }

    @Override
    public void onBookDetailsResponseFailure(String message) {
        ShowToast.withMessage("something wrong");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_arrow:
                BookDetailsActivity.this.finish();
                break;

        }
    }


    private class BookDetailsRecyclerViewAdapter extends AppRecyclerViewAdapter  {

        private final int TYPE_HEADER=1;
        private final int TYPE_FOOTER=2;

    @Override
    public void add(Object object) {
        bookdata=DefineClassType.getType(object,BookDetails.class);

    }

    @Override
    public void clear() {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==1){
            View itemview=LayoutInflater.from(getApplicationContext()).inflate(R.layout.header_book_details,parent,false);
            return new VhHeader(itemview);
        }
        else if(viewType==2){
            View itemview=LayoutInflater.from(getApplicationContext()).inflate(R.layout.footer_book_details,parent,false);
            return new VhInnerFooter(itemview);
        }
        throw new RuntimeException("View type doesnot match Found");

    }


    @Override
    public int getItemViewType(int position) {
        if(isTypeHeader(position)){
            return TYPE_HEADER;
        }
        return TYPE_FOOTER;


    }
    private boolean isTypeHeader(int position) {
        return position == 0;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VhHeader){
            VhHeader vhHeader= (VhHeader) holder;

            vhHeader.bookname.setText(bookdata.getBook().getName());


            Glide.with(MyApplication.getAppContext()).load(bookdata.getBook().getFeaturedImage()).into(vhHeader.bookcover);
            vhHeader.bookauthor.setText(bookdata.getBook().getAuthorName());
            vhHeader.date.setText(UtilitiesFunctions.simpleFormatToClientDateOnly(bookdata.getBook().getPublishedDate()));
            vhHeader.details.setText(UtilitiesFunctions.fromHtml(bookdata.getBook().getDescription()));

        }
        else if (holder instanceof VhInnerFooter){
            VhInnerFooter vhInnerFooter= (VhInnerFooter) holder;
            preapreInnerRecyclerView(vhInnerFooter.FooterRecyclerView,bookdata.getSimilarBooks());


        }


    }

    private void preapreInnerRecyclerView(RecyclerView footerRecyclerView,List<BookDetails.SimilarBook>similarBookList) {
        InnerFooterRecyclerViewAdapter innerFooterRecyclerViewAdapter=new InnerFooterRecyclerViewAdapter(similarBookList);
        LinearLayoutManager linearLayoutManager=
                new LinearLayoutManager(BookDetailsActivity.this,RecyclerView.HORIZONTAL,false);
        footerRecyclerView.setLayoutManager(linearLayoutManager);
        footerRecyclerView.setAdapter(innerFooterRecyclerViewAdapter);

    }

    @Override
    public int getItemCount() {

        if(bookdata!=null){

            if(bookdata.getBook()!=null){
                return 2;
            }


        }

      return 0;
    }

    private class VhHeader extends RecyclerView.ViewHolder {
        private ImageView bookcover;
        private TextView bookname;
        private TextView bookauthor;
        private TextView date;
        private TextView details;



        public VhHeader(View itemview) {
            super(itemview);
            bookcover=itemview.findViewById(R.id.details_book_image);
            bookname=itemview.findViewById(R.id.details_book_name);
            bookauthor=itemview.findViewById(R.id.details_book_author);
            date=itemview.findViewById(R.id.details_book_date);
            details=itemview.findViewById(R.id.details_book_details);
        }
    }


}

    private class InnerFooterRecyclerViewAdapter extends AppRecyclerViewAdapter{
        private List<BookDetails.SimilarBook> similarBookList;
        public InnerFooterRecyclerViewAdapter(List<BookDetails.SimilarBook> similarBookList)
        {
            this.similarBookList=similarBookList;
        }


        @Override
        public void add(Object object) {
        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemview=LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_similar_book,parent,false);

            return new VhInnerFooter(itemview);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            VhInnerFooter vhInnerFooter= (VhInnerFooter) holder;
            vhInnerFooter.bookTitle.setText(similarBookList.get(position).getName());
            Glide.with(MyApplication.getAppContext()).load(similarBookList.get(position).getFeaturedImage()).into(vhInnerFooter.bookImage);

        }

        @Override
        public int getItemCount() {
            if(similarBookList!=null){
                return similarBookList.size();
            }
            return 0;
        }
    }
    private class VhInnerFooter extends RecyclerView.ViewHolder
//            implements View.OnClickListener
    {
        private RecyclerView FooterRecyclerView;
        private ImageView bookImage;
        private TextView bookTitle;
        public VhInnerFooter(View itemview) {
            super(itemview);
            FooterRecyclerView=itemview.findViewById(R.id.footer_book_details_recycler_view);
            bookImage=itemview.findViewById(R.id.item_book_cover);
            bookTitle=itemview.findViewById(R.id.item_book_name);
//            bookImage.setOnClickListener(this);


        }

//        @Override
//        public void onClick(View v) {
//            switch (v.getId()){
//                case R.id.item_book_cover:
//                    Intent intent =new Intent(BookDetailsActivity.this,BookDetailsActivity.class);
////                    int a=bookdata.getSimilarBooks().get(getAdapterPosition()).getId();
////                    Log.e("onClick: ", "book id"+a);
//                    intent.putExtra("bookid",bookdata.getSimilarBooks().get(getAdapterPosition()).getId().toString());
//
////                    intent.putExtra("bookid",a);
//                    startActivity(intent);
//                    break;
//            }
//        }

//        @Override
//        public void onClick(View v) {
//            switch (v.getId()){
//                case R.id.item_book_cover:
//                    Intent intent =new Intent(BookDetailsActivity.this,BookDetailsActivity.class);
//                    int a=bookdata.getSimilarBooks().get(getAdapterPosition()).getId();
//                    Log.e("onClick: ", "book id"+a);
//
//                    intent.putExtra("bookid",a);
//                    startActivity(intent);
//                    break;
//            }
//
//        }
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()){
//                case R.id.similarbooks:
//                    Intent intent =new Intent(BookDetailsActivity.this,BookDetailsActivity.class);
//                    startActivity(intent);
//
//            }
//        }


//        @Override
//        public void onClick(View view) {
//
//            switch (view.getId()){
//                case R.id.similar_books:
//                    Intent intent =new Intent(BookDetailsActivity.this,BookDetailsActivity.class);
//                    int s=bookdata.getSimilarBooks().get(getAdapterPosition()).getId();
//            int s=searchData.getData().get(getAdapterPosition()).getId();
//                    Log.e("onClick: ", "book id"+s);
//
//                    intent.putExtra("bookid",s);
//
//
//                    startActivity(intent);
//
//            }
//
//        }
    }





}
