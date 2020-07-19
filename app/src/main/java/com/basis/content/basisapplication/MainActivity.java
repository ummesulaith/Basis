package com.basis.content.basisapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

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

    CardStackView cardStackView;
    CardStackLayoutManager manager;

    private GestureDetectorCompat mDetector;
    CardStackLayoutManagerAdapter adapter;
    ProgressDialog proDialog;

    private ArrayList<Contentdata> mContent = new ArrayList<>() ;
    ArrayList<String> result = new ArrayList<>();

    CompositeDisposable compositeDisposable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardStackView = (CardStackView)findViewById(R.id.card_stack_view);

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
        compositeDisposable = new CompositeDisposable();

        Invoke();



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
//             proDialog = ProgressDialog.show(this, "title", "message");

            initCardStackView();
//            proDialog.dismiss();
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




    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    private void Invoke()
    {
        proDialog = ProgressDialog.show(this, "Please Wait", "Data is loading...");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MyJsonConverter.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
                Data_retrofitInterface api = retrofit.create(Data_retrofitInterface.class);

                initCardStackView();

                compositeDisposable.add(api.getdata()
             .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(mContent -> {

                            if (mContent.getData() != null &&
                                    mContent.getData().size() > 0) {


                                Log.i(TAG,"Chekcing response");

                                for (int i = 0;i<mContent.getData().size();i++)
                                {

                                   result.add(mContent.getData().get(i).getText());
                                    Log.i(TAG,"Chekcing response"+mContent.getData().get(i).getText());

                                }
                                proDialog.dismiss();
                                adapter = new CardStackLayoutManagerAdapter(result,this);
                                cardStackView.setAdapter(adapter);

                                    } else
                                        Toast.makeText(this, "No data found!", Toast.LENGTH_SHORT).show();
                                }
                            ));


    }


private void initCardStackView()
{
    manager = new CardStackLayoutManager(this,this);


    cardStackView.setLayoutManager(manager);

    Log.i(TAG,"Adpater"+adapter);
    manager.setStackFrom(StackFrom.Bottom);
    manager.setCanScrollHorizontal(true);
    manager.setCanScrollVertical(true);
    manager.setVisibleCount(3);
    manager.setTranslationInterval(8f);
    manager.setDirections(Direction.FREEDOM);
    manager.setVisibleCount(3);





}



}
