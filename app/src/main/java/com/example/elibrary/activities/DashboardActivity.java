package com.example.elibrary.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.elibrary.R;
import com.example.elibrary.VideoActivity;
import com.example.elibrary.helpers.AppActivity;
import com.example.elibrary.helpers.AppRecyclerViewAdapter;
import com.example.elibrary.helpers.DefineClassType;
import com.example.elibrary.helpers.MyApplication;
import com.example.elibrary.models.Home;
import com.example.elibrary.presenters.HomePresenter;
import com.google.gson.GsonBuilder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppActivity implements View.OnClickListener, HomePresenter.View {
    private ImageView imageView;
    private ImageView notification;
    private HomeRecyclerViewAdapter homeRecyclerViewAdapter;
    private HeaderRecycLerViewAdapter headerRecycLerViewAdapter;
    private ItemRecyclerViewAdapter itemRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private HomePresenter homePresenter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initializedView();
        initializedListners();
        prepareRecyclerView();

    }

    private void prepareRecyclerView() {
        homeRecyclerViewAdapter=new HomeRecyclerViewAdapter();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DashboardActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(homeRecyclerViewAdapter);


    }

    @Override
    protected void initializedView() {
        imageView=findViewById(R.id.search) ;
        notification=findViewById(R.id.notifications);
        recyclerView=findViewById(R.id.home_recycler_view);


    }

    @Override
    protected void initializedListners() {
        imageView.setOnClickListener(this);
        notification.setOnClickListener(this);
        homePresenter=new HomePresenter(this);
        homePresenter.home();


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.search:
                startActivity(new Intent(DashboardActivity.this,SearchActivity.class));
                break;


            case R.id.notifications:
                startActivity(new Intent(DashboardActivity.this,NotificationActivity.class));
                break;
        }

    }

    @Override
    public void onHomeResponseSuccess(Home home) {
        homeRecyclerViewAdapter.add(home);
        homeRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void onHomeResponseFailure(String message) {

    }



    public class HomeRecyclerViewAdapter extends AppRecyclerViewAdapter{
        private final int TYPE_HEADER=1;
        private final int TYPE_ITEM=2;



        private Home homeModel;

        @Override
        public void add(Object object) {
            homeModel= DefineClassType.getType(object,Home.class);

        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           if(viewType==1){
               View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_categories,parent,false);
                return new VHHeader(itemView);
           }
           else if(viewType==2){
               View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_books,parent,false);
               return new VHItem(itemView);
            }



           throw new RuntimeException("View Type Doesnot Exit");
        }

        @Override
        public int getItemViewType(int position) {
            if(isTypeHeader(position)){
                return TYPE_HEADER;
            }
            return TYPE_ITEM;
        }

        private boolean isTypeHeader(int position) {
            return position==0;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof VHHeader){
                VHHeader vhHeader= (VHHeader) holder;
                //category list
                prepareHeaderRecyclerView(vhHeader.headerrecyclerview,homeModel.getCategoriesList());
            }
            else if(holder instanceof VHItem){
                VHItem vhItem= (VHItem) holder;
                List<Home.HomeList> homeLists=homeModel.getHomeList();
                vhItem.title.setText(homeLists.get(position-1).getTitle());
                prepareItemRecyclerView(vhItem.recyclerView,homeLists.get(position-1).getBooklists());

            }

        }

        @Override
        public int getItemCount() {
            if (homeModel != null) {
                return homeModel.getHomeList().size()+1 ;

                //+1
            }
            return 0;

        }
    }

    private void prepareHeaderRecyclerView(RecyclerView headerrecyclerview, List<Home.CategoriesList> categoriesList) {
    headerRecycLerViewAdapter=new HeaderRecycLerViewAdapter(categoriesList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DashboardActivity.this,RecyclerView.HORIZONTAL,false);
        headerrecyclerview.setLayoutManager(linearLayoutManager);
        headerrecyclerview.setAdapter(headerRecycLerViewAdapter);
    }
    private void prepareItemRecyclerView(RecyclerView itemrecyclerview,List<Home.Booklist> booklists){
        itemRecyclerViewAdapter=new ItemRecyclerViewAdapter(booklists);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DashboardActivity.this,RecyclerView.HORIZONTAL,false);
        itemrecyclerview.setLayoutManager(linearLayoutManager);
        itemrecyclerview.setAdapter(itemRecyclerViewAdapter);

    }

    public class HeaderRecycLerViewAdapter extends AppRecyclerViewAdapter{
        private List<Home.CategoriesList> mainCategoryList;

        public HeaderRecycLerViewAdapter(List<Home.CategoriesList> mainCategoryList){
            this.mainCategoryList=mainCategoryList;
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
            View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_header_content,parent,false);
            return new VHHeaderContent(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            VHHeaderContent vhHeaderContent= (VHHeaderContent) holder;
            Home.CategoriesList categoriesList=mainCategoryList.get(position);
            vhHeaderContent.categoryname.setText(categoriesList.getName());
            Glide.with(MyApplication.getAppContext()).load(categoriesList.getImage()).into(vhHeaderContent.categorycover);

        }

        @Override
        public int getItemCount() {
            return mainCategoryList.size();
        }
    }


    private class VHHeader extends RecyclerView.ViewHolder {
        private RecyclerView headerrecyclerview;
        public VHHeader(View itemView) {
            super(itemView);
            headerrecyclerview=itemView.findViewById(R.id.categories_recycler_view);

        }
    }

    private class VHItem extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private TextView title;
        public VHItem(View itemView) {
            super(itemView);
            recyclerView=itemView.findViewById(R.id.header_recycler_view);
            title=itemView.findViewById(R.id.item_title);
        }
    }

    private class VHHeaderContent extends RecyclerView.ViewHolder {
        private CircleImageView categorycover;
        private TextView categoryname ;
        public VHHeaderContent(View itemView) {
            super(itemView);
            categorycover=itemView.findViewById(R.id.categoryimage);
            categoryname=itemView.findViewById(R.id.categorytext);
        }
    }

    public class ItemRecyclerViewAdapter extends AppRecyclerViewAdapter{
        public List<Home.Booklist> books;


        public ItemRecyclerViewAdapter(List<Home.Booklist> booklists) {
           this.books = booklists;

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
            View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_books,parent,false);
            return new VhItemContent(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            VhItemContent vhItemContent= (VhItemContent) holder;
             Home.Booklist booklist=books.get(position);
            String booktype=booklist.getType();
            switch (booktype){
                case "File":
                   vhItemContent.readbtn.setText("Read");
                    break;
                case "Video" :
                    vhItemContent.readbtn.setText("Watch");
                    break;
            }

            vhItemContent.booktitle.setText(booklist.getName());
            vhItemContent.author.setText(booklist.getAuthorName());
            Glide.with(MyApplication.getAppContext()).load(booklist.getFeaturedImage()).into(vhItemContent.bookcover);



        }

        @Override
        public int getItemCount() {
            return books.size();
        }
        private class VhItemContent extends RecyclerView.ViewHolder {
            private ImageView bookcover;
            private TextView booktitle;
            private TextView author;
            private Button readbtn;


            public VhItemContent(View itemView) {

                super(itemView);

                bookcover=itemView.findViewById(R.id.latest_book_cover);
                booktitle=itemView.findViewById(R.id.book_name);
                author=itemView.findViewById(R.id.author);
                readbtn=itemView.findViewById(R.id.btn_read);



                readbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Home.Booklist booklist=books.get(getAdapterPosition());

                        Intent intent=new Intent(DashboardActivity.this,PDFReaderActivity.class);
                        intent.putExtra("bookuri",booklist.getUrl());
                        intent.putExtra("bookname",booklist.getName());
                        startActivity(intent);

////Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(booklist.getUrl()));
//                        Intent intent2=new Intent(DashboardActivity.this, VideoActivity.class);
////                        intent.setData(Uri.parse(booklist.getUrl()));
//                        intent2.putExtra("bookurl",booklist.getUrl());
//                        Log.e( "onClick: ", new GsonBuilder().create().toJson(booklist.getUrl()));
//                        startActivity(intent2);

                    }
                });


            }


        }
    }


}
