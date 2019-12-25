package com.example.elibrary.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.elibrary.R;
import com.example.elibrary.helpers.AppActivity;
import com.example.elibrary.helpers.AppRecyclerViewAdapter;
import com.example.elibrary.helpers.DefineClassType;
import com.example.elibrary.helpers.MyApplication;
import com.example.elibrary.models.Notification;
import com.example.elibrary.presenters.NotificationPresenter;
import com.example.elibrary.utilis.UtilitiesFunctions;
import com.facebook.shimmer.ShimmerFrameLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationActivity extends AppActivity implements NotificationPresenter.View, View.OnClickListener {
    private ImageView imageView;
    private RecyclerView recyclerView;
    private NotificationPresenter notificationPresenter;
    private NotificationRecycLerViewAdapter notificationRecycLerViewAdapter;
    private ShimmerFrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initializedView();
        initializedListners();
        prepareRecyclerView();
    }



    @Override
    protected void initializedView() {
        imageView=findViewById(R.id.back_arrow);
        recyclerView=findViewById(R.id.noti_recycler_view);
        frameLayout=findViewById(R.id.shimmerlayout);


    }

    @Override
    protected void initializedListners() {
        notificationPresenter=new NotificationPresenter(this);
        imageView.setOnClickListener(this);
        notificationPresenter.notification();



    }

    @Override
    protected void onResume() {
        super.onResume();
        frameLayout.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        frameLayout.stopShimmer();
    }

    @Override
    public void onNotificationResponseSuccess(Notification notification) {
        notificationRecycLerViewAdapter.add(notification);
        notificationRecycLerViewAdapter.notifyDataSetChanged();
        frameLayout.stopShimmer();
        frameLayout.setVisibility(View.GONE);



    }

    @Override
    public void onNotificationResponseFailure(String message) {
        frameLayout.stopShimmer();
        frameLayout.setVisibility(View.GONE);

    }


    private void prepareRecyclerView() {
        notificationRecycLerViewAdapter=new NotificationRecycLerViewAdapter();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(NotificationActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(notificationRecycLerViewAdapter);


    }

    private class NotificationRecycLerViewAdapter extends AppRecyclerViewAdapter {
        private Notification notificationdata;

        @Override
        public void add(Object object) {
            notificationdata= DefineClassType.getType(object,Notification.class);

        }

        @Override
        public void clear() {

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.item_notification_content,parent,false);
            return new VHItem(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            VHItem vhItem= (VHItem) holder;
            Notification.Datum datum=notificationdata.getData().get(position);
            vhItem.booktitle.setText(UtilitiesFunctions.fromHtml(datum.getName()));
            vhItem.bookdescription.setText(UtilitiesFunctions.fromHtml(datum.getDescription()));
            vhItem.booktime.setText(UtilitiesFunctions.durationEnglish(datum.getCreatedAt()));


        }

        @Override
        public int getItemCount() {
            if(notificationdata!=null){
                return notificationdata.getData().size();
            }
            return 0;
        }

        private class VHItem extends RecyclerView.ViewHolder {
            private TextView booktitle;
            private TextView bookdescription;
            private TextView booktime;
            public VHItem(View view) {
                super(view);

                booktitle=view.findViewById(R.id.noti_book_title);
                bookdescription=view.findViewById(R.id.noti_book_description);
                booktime=view.findViewById(R.id.noti_book_date);


            }
        }
    }





    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.back_arrow:
                NotificationActivity.this.finish();
                break;
        }
    }
}
