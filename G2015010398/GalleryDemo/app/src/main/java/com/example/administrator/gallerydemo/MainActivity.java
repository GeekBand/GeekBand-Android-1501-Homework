package com.example.administrator.gallerydemo;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends Activity {
    private LinearLayout mGallery;
    private int[] mImgIds;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInflater = LayoutInflater.from(this);
        initData();
        initView();

    }
    private void initData()
    {
        mImgIds = new int[] { R.drawable.a, R.drawable.b, R.drawable.c,
                R.drawable.d };
    }

    private void initView()
    {
        mGallery = (LinearLayout) findViewById(R.id.gallery);

        for (int i = 0; i < mImgIds.length; i++)
        {

            View view = mInflater.inflate(R.layout.activity_index_gallery_item,
                    mGallery, false);
            ImageView img = (ImageView) view
                    .findViewById(R.id.id_index_gallery_item_image);
            img.setImageResource(mImgIds[i]);
            TextView txt = (TextView) view
                    .findViewById(R.id.id_index_gallery_item_text);
            txt.setText("some info ");
            mGallery.addView(view);
        }
    }






}
