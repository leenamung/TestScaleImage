package com.example.administrator.testScaleImage;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015-04-21.
 */
public class mViewPagerAdapter extends PagerAdapter {
    mImageView[] img = new mImageView[3];

    public mViewPagerAdapter(Context context, int offset){
        img[0] = (mImageView)((Activity)context).getLayoutInflater().inflate(R.layout.viewpager_item, null);
        img[1] = (mImageView)((Activity)context).getLayoutInflater().inflate(R.layout.viewpager_item, null);
        img[2] = (mImageView)((Activity)context).getLayoutInflater().inflate(R.layout.viewpager_item, null);

        img[0].setImageResource(R.drawable.a);
        img[0].setScaleType(ImageView.ScaleType.MATRIX);
        img[1].setImageResource(R.drawable.b);
        img[1].setScaleType(ImageView.ScaleType.MATRIX);
        img[2].setImageResource(R.drawable.c);
        img[2].setScaleType(ImageView.ScaleType.MATRIX);
        for(mImageView m : img)
            m.initImage(offset);
    }
    private reScaleImage rescale;

    public reScaleImage getReScaleInterface(){
        if(rescale == null)
            rescale = new reScaleImage() {
                @Override
                public void reScaleImage(int scrollY) {
                    for(mImageView m : img)
                        m.reScaleImage(scrollY);
                }
            };
        return rescale;
    }

    public interface reScaleImage{
        public void reScaleImage(int scrollY);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(img[position]);
        return img[position];
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}
