package com.luckydoglee.horizontalscrollview;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    private LinearLayout myGallery;
    private int[] myImgIds;
    private LayoutInflater myInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        myInflater = LayoutInflater.from(this);
        initData();
        initView();
    }

    private void initData(){
        myImgIds = new int[] { R.drawable.hailijian, R.drawable.mianxianhu, R.drawable.rouzong,
                R.drawable.shachamian, R.drawable.tusundong, R.drawable.wuxiang, R.drawable.zha};
    }

    private void initView(){
        myGallery = (LinearLayout)findViewById(R.id.gallery);

        for(int i = 0; i < myImgIds.length; i++){
            View view = myInflater.inflate(R.layout.food, myGallery, false);
            ImageView imageView = (ImageView)view.findViewById(R.id.food_image);
            imageView.setImageResource(myImgIds[i]);
            imageView.setId(i);
            myGallery.addView(view);
        }
    }

}