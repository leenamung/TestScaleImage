package com.example.administrator.testScaleImage;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015-04-21.
 */
public class mImageView extends ImageView {
    private static final int WIDTH = 0;
    private static final int HEIGHT = 1;

    int offset = 0;

    public mImageView(Context context) {
        super(context);
    }

    public mImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public mImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void initImage(int offset){
        this.offset = offset;
        Matrix matrix = getImageMatrix();
        float[] value = new float[9];
        matrix.getValues(value);

        // 뷰 크기
        int width = MainActivity.ScreenWidth;
        int height = MainActivity.ScreenHeight;

        // 이미지 크기
        Drawable d = this.getDrawable();
        if (d == null)  return;
        int imageWidth = d.getIntrinsicWidth();
        int imageHeight = d.getIntrinsicHeight();
        int scaleWidth = (int) (imageWidth * value[0]);
        int scaleHeight = (int) (imageHeight * value[4]);

        value[2] = 0;
        value[5] = 0;

        if (imageWidth > width || imageHeight > height){
            int target = WIDTH;
            if (imageWidth < imageHeight) target = HEIGHT;

            if (target == WIDTH) value[0] = value[4] = (float)width / imageWidth;
            if (target == HEIGHT) value[0] = value[4] = (float)height / imageHeight;

            scaleWidth = (int) (imageWidth * value[0]);
            scaleHeight = (int) (imageHeight * value[4]);

            if (scaleWidth > width) value[0] = value[4] = (float)width / imageWidth;
            if (scaleHeight > height) value[0] = value[4] = (float)height / imageHeight;
        }

        // 그리고 가운데 위치하도록 한다.
        scaleWidth = (int) (imageWidth * value[0]);
        scaleHeight = (int) (imageHeight * value[4]);
        if (scaleWidth < width){
            value[2] = (float) width / 2 - (float)scaleWidth / 2;
        }
        if (scaleHeight < height){
            value[5] = (float) height / 2 - (float)scaleHeight / 2 + (float)offset / 2;
        }

        matrix.setValues(value);
        setImageMatrix(matrix);
    }

    public void reScaleImage(int scrollY){
        Matrix matrix = getImageMatrix();
        float[] value = new float[9];
        matrix.getValues(value);

        // 뷰 크기
        int width = MainActivity.ScreenWidth;
        int height = MainActivity.ScreenHeight;

        // 이미지 크기
        Drawable d = this.getDrawable();
        if (d == null)  return;
        int imageWidth = d.getIntrinsicWidth();
        int imageHeight = d.getIntrinsicHeight();
        int scaleWidth = (int) (imageWidth * value[0]);
        int scaleHeight = (int) (imageHeight * value[4]);

        value[2] = 0;
        value[5] = 0;

        value[0] = value[4] = 1+(float)(offset+scrollY)/(float)offset;



        if (scaleWidth > width || scaleHeight > height){
            int target = WIDTH;
            if (scaleWidth < scaleHeight) target = HEIGHT;

            if (target == WIDTH) value[0] = value[4] = (float)width / imageWidth;
            if (target == HEIGHT) value[0] = value[4] = (float)height / imageHeight;

            scaleWidth = (int) (imageWidth * value[0]);
            scaleHeight = (int) (imageHeight * value[4]);

            if (scaleWidth > width) value[0] = value[4] = (float)width / imageWidth;
            if (scaleHeight > height) value[0] = value[4] = (float)height / imageHeight;
        }

        // 그리고 가운데 위치하도록 한다.
        scaleWidth = (int) (imageWidth * value[0]);
        scaleHeight = (int) (imageHeight * value[4]);
        if (scaleWidth < width){
            value[2] = (float) width / 2 - (float)scaleWidth / 2;
        }
        if (scaleHeight < height){
            value[5] = (float) height / 2 - (float)scaleHeight / 2 + (float)(-scrollY) / 2;
        }


        matrix.setValues(value);
        setImageMatrix(matrix);
        invalidate();
    }


    @Override
    public Matrix getImageMatrix() {
        return super.getImageMatrix();
    }
}
