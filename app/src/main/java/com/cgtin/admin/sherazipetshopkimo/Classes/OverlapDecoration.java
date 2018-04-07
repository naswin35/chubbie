package com.cgtin.admin.sherazipetshopkimo.Classes;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Admin on 06-12-2017.
 **/



    public class OverlapDecoration extends RecyclerView.ItemDecoration {

        private final static int vertOverlap = -300;

        @Override
        public void getItemOffsets (Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            int position = parent.getChildAdapterPosition(view);
            if (position == 0) {

                outRect.set(0, 0, 0, 0);

            } else {
                outRect.set(0, vertOverlap, 0, 0);
            }



        }

    public static float convertPixelsToDp(int px){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        return (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, px, displaymetrics );
    }





}



