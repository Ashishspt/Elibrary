package com.example.elibrary.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.elibrary.R;
import com.example.elibrary.helpers.AppActivity;
import com.example.elibrary.helpers.AppRecyclerViewAdapter;
import com.example.elibrary.helpers.DefineClassType;
import com.example.elibrary.helpers.MyApplication;
import com.example.elibrary.models.Login;
import com.example.elibrary.models.Search;
import com.example.elibrary.presenters.SearchPresenter;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchActivity extends AppActivity implements SearchPresenter.View, SearchView.OnQueryTextListener {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private SearchPresenter searchPresenter;
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initializedView();
        initializedListners();
        prepareRecyclerView();
    }



    @Override
    protected void initializedView() {
       searchView=findViewById(R.id.search_view_bar);
        recyclerView=findViewById(R.id.search_recycler_view);


    }

    @Override
    protected void initializedListners() {
        searchPresenter=new SearchPresenter(this);
        searchView.setOnQueryTextListener(this);

    }

    @Override
    public void onSearchResponseSuccess(Search search) {
        searchRecyclerViewAdapter.add(search);
        searchRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void onSearchResponseFailure(String message) {
        Log.e( "onSearchFailure: ","failure" );

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchPresenter.searchBook(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchPresenter.searchBook(newText);
        return false;
    }

    private void prepareRecyclerView() {
        searchRecyclerViewAdapter=new SearchRecyclerViewAdapter();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(SearchActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(searchRecyclerViewAdapter);


    }

    private  class SearchRecyclerViewAdapter extends AppRecyclerViewAdapter{
        private Search searchData;

        @Override
        public void add(Object object) {
            searchData= DefineClassType.getType(object,Search.class);


        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_book,parent,false);
            return new VHItem(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            VHItem vhItem= (VHItem) holder;
           Search.Datum datum=searchData.getData().get(position);
           vhItem.bookname.setText(datum.getName());
           vhItem.bookdescription.setText(datum.getDescription());
            Glide.with(MyApplication.getAppContext()).load(datum.getFeaturedImage()).into(vhItem.imageView);



        }

        @Override
        public int getItemCount() {

            if(searchData!=null){
                return searchData.getData().size();
            }
            return 0;
        }

        private class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener {
            private CircleImageView imageView;
            private TextView bookname;
            private TextView bookdescription;
            private LinearLayout searchbooklist;

            public VHItem(View itemView) {
                super(itemView);
                imageView=itemView.findViewById(R.id.book_image);
                bookname=itemView.findViewById(R.id.book_title);
                bookdescription=itemView.findViewById(R.id.book_description);
                searchbooklist=itemView.findViewById(R.id.search_book);

                searchbooklist.setOnClickListener(this);


            }

            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SearchActivity.this,BookDetailsActivity.class);
//                int s=searchData.getData().get(getAdapterPosition()).getId();
//                Log.e("onClick: ", "book id"+s);
//
//                intent.putExtra("bookid",s);
                intent.putExtra("bookid",searchData.getData().get(getAdapterPosition()).getId().toString());


                startActivity(intent);




            }
        }
    }
}
