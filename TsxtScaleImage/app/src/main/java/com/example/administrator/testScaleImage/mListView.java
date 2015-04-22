package com.example.administrator.testScaleImage;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2015-04-21.
 */
public class mListView extends ListView {

    OnScrollListener mScroll = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (mOnScrollListener != null)
                mOnScrollListener.onScrollStateChanged(view, scrollState);
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if(mOnScrollListener != null)
                mOnScrollListener.onScroll(view , firstVisibleItem, visibleItemCount, totalItemCount);
            View v = getChildAt(0);
            if(v != null && mOnSCrolling != null && getFirstVisiblePosition() == 0) {
                int top = v.getTop();
                if (top >= 0)
                    mOnSCrolling.FullScreen();
            }
            onScrollChanged();
        }
    };

    OnScrollListener mOnScrollListener;

    onScrolling mOnSCrolling;

    ViewPager pager;
    int offset;

    boolean set_another_listener = false;
    boolean fullscreen = false;

    boolean touchable = true;

    mViewPagerAdapter.reScaleImage mReScale;


    public mListView(Context context) {
        super(context);
    }

    public mListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public mListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("ev.action", ev.getAction()+"");
        if(!touchable)
            return false;

        if(getFirstVisiblePosition() == 0) {

            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                   Toggle();
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        if(set_another_listener)
            mOnScrollListener = l;
        else
            super.setOnScrollListener(l);
    }


    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        setSelectionFromTop(0, -offset);
    }

    public void init(final int offset, final int height) {
        setOnScrollListener(mScroll);
        set_another_listener = true;
        this.offset = offset;
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(initHeader(), ViewGroup.LayoutParams.MATCH_PARENT, height+offset);
        initViewPager();
        addHeaderView(layout);
    }

    LinearLayout navibox;
    View p[] = new View[3];
    private RelativeLayout initHeader(){
        RelativeLayout header = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.listview_header, null);

        navibox = (LinearLayout)header.findViewById(R.id.navibox);
        pager = (ViewPager)header.findViewById(R.id.pager);
        p[0] = header.findViewById(R.id.p1);
        p[1] = header.findViewById(R.id.p2);
        p[2] = header.findViewById(R.id.p3);
        return header;
    }

    private void initViewPager(){

        mViewPagerAdapter adapter = new mViewPagerAdapter(getContext(), offset);
        pager.setAdapter(adapter);
        mReScale = adapter.getReScaleInterface();
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrolled(int position, float positionOffest,
                                       int positionOffsetPixels) {
                // TODO Auto-generated method stub
                if (mOnSCrolling != null) {
                    mOnSCrolling.onPagerScrolling(position, positionOffest, positionOffsetPixels);
                }
                for(int i = 0 ; i < 3; i++){
                    p[i].setBackgroundColor(Color.parseColor("#550000"));
                }
                p[position].setBackgroundColor(Color.parseColor("#55ff00"));
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void setOnScrolling(final onScrolling onScroll){
        mOnSCrolling = onScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(fullscreen)
            return false;


        return super.onInterceptTouchEvent(ev);
    }

    private void Toggle(){
        View v = getChildAt(0);
        if( v != null && mOnSCrolling != null && getFirstVisiblePosition() == 0) {

            int halfoffset = (int) ((float) offset / 2f);

            int top = v.getTop();

            if (-offset < top && top <= -halfoffset) {
                setSmallScreen();
            } else if (-halfoffset < top && top < 0) {
                setFullScreen();
            }
        }
    }

    private void setFullScreen(){
        setSelectionFromTop(0, 0);
    }

    private void setSmallScreen(){
        setSelectionFromTop(0, -offset);
    }

    private void onScrollChanged() {
        if(getChildAt(0) == null)
            return;
        View v1 = getChildAt(0);
        int top1 = (v1 == null) ? 0 : v1.getTop();
        // This check is needed because when the first element reaches the top of the window, the top values from top are not longer valid.
        if(mOnSCrolling != null)
            mOnSCrolling.onScrolling(top1, getFirstVisiblePosition());
        if(mReScale != null)
            mReScale.reScaleImage(top1);
    }

    public interface onScrolling{
        public void onScrolling(int Top, int FirstVisiblePosition);
        public void FullScreen();
        public void onPagerScrolling(int position, float positionOffest,
                                     int positionOffsetPixels);
    }
}
