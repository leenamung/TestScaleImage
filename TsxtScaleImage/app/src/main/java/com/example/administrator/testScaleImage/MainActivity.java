package com.example.administrator.testScaleImage;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {

    public static int ScreenWidth = 0;
    public static int ScreenHeight = 0;
    mListView mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getScreenSize();

        initListView();
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void initListView(){
        mlist = (mListView)findViewById(R.id.listview);


        final int offset = (int)((float)ScreenHeight/3f);
        int height = ScreenHeight - offset;
        mlist.init(offset, height);

        ArrayList<String> item = new ArrayList<>();

        while(item.size() < 50)
            item.add(new String(item.size()+""));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,item);
        mlist.setAdapter(adapter);

        mlist.setOnScrolling(new mListView.onScrolling() {
            @Override
            public void onScrolling(int Top, int FirstVisiblePosition) {

            }

            @Override
            public void FullScreen() {
                Toast.makeText(MainActivity.this, "FullScreen", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPagerScrolling(int position, float positionOffest, int positionOffsetPixels) {

            }
        });
    }

    private void getScreenSize(){
        WindowManager windowManager = getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        ScreenWidth = dm.widthPixels;
        ScreenHeight = dm.heightPixels - getStatusBarHeight();
    }

}
