package com.basis.content.basisapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.view.animation.DecelerateInterpolator;

import com.basis.content.basisapplication.Adapter.CardStackLayoutManagerAdapter;
import com.basis.content.basisapplication.Model.Contentdata;
import com.basis.content.basisapplication.RetrofitInterface.Data_retrofitInterface;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.StackFrom;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CardStackListener {

    private String BASE_URL = "https://gist.githubusercontent.com/anishbajpai014/d482191cb4fff429333c5ec64b38c197/raw/b11f56c3177a9ddc6649288c80a004e7df41e3b9/";


    private static final String TAG = "Swipe";
    private ArrayList<String> mContent = new ArrayList<>();
    CardStackView cardStackView;
    CardStackLayoutManager manager;

    private GestureDetectorCompat mDetector;
    CardStackLayoutManagerAdapter adapter;
    ProgressDialog proDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardStackView = (CardStackView)findViewById(R.id.card_stack_view);

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        initData();
        InvokeRetrofit();


    }

    private void initData()
    {
        mContent.add("\n" +
                "            \"Text\": \"Felis long weekend in diameter and hatred nutrition and convenience. " +
                "It imperdie than one casino each. Sem players sterilized sauce to the sauce life. Quiver or ugly or need now");

        mContent.add("Cancel football tomorrow, but the arc Ut et drink recipes. " +
                "Lobortis a lot of taste chili in. The latest television ecological website link to propaganda sometimes.");

        mContent.add("Competition thermal Holder Place Holder volleyball soccer kids are sad." +
                " Drink at least here in the quiver or even ugly now. Sed carrots airline does not need to focus on soccer");


        initCardStackView();
    }
    private void initCardStackView()
    {
         manager = new CardStackLayoutManager(this,this);
         adapter = new CardStackLayoutManagerAdapter(mContent,this);

        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        manager.setStackFrom(StackFrom.Bottom);
        manager.setCanScrollHorizontal(true);
        manager.setCanScrollVertical(true);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8f);
        manager.setDirections(Direction.FREEDOM);
        manager.setVisibleCount(3);





    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {
        Toast.makeText(getApplicationContext()," dragging"+direction,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onCardSwiped(Direction direction) {
            Toast.makeText(getApplicationContext()," Direction "+direction,Toast.LENGTH_LONG).show();


        if (manager.getTopPosition() == adapter.getItemCount()) {
            // -------------------- last position reached, do something ---------------------
             proDialog = ProgressDialog.show(this, "title", "message");

        initData();

            proDialog.dismiss();
        }


    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {
        Log.i(TAG,"Card Appeared position"+position);




    }

    @Override
    public void onCardDisappeared(View view, int position) {
        Log.i(TAG,"position"+position);

    }



    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG,"onDown: " + event.toString());
            RewindAnimationSetting setting = new RewindAnimationSetting.Builder()
                    .setDirection(Direction.Bottom)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(new DecelerateInterpolator())
                    .build();
            manager.setRewindAnimationSetting(setting);
            cardStackView.rewind();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
            return true;
        }
    }

    private void InvokeRetrofit()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        Data_retrofitInterface api = retrofit.create(Data_retrofitInterface.class);
        Call<Contentdata> call = api.getdata();
        call.enqueue(new Callback<Contentdata>() {
            @Override
            public void onResponse(Call<Contentdata> call, Response<Contentdata> response) {

                if (response.isSuccessful())
                {
                    Log.i(TAG,"Response isSuccess"+response.code());



                }else
                {
                    Log.i(TAG,"Response"+response.message());

                }


            }

            @Override
            public void onFailure(Call<Contentdata> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i(TAG,"Response failure"+t.getMessage());



            }
        });
    }



}
