package com.ffl.nytimesearch.listerners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by PJS on 7/30/2017.
 */

public class RecyclerItemClickListerner implements RecyclerView.OnItemTouchListener {

    public static interface OnItemClickListerner
    {
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }

    private OnItemClickListerner mListener;
    private GestureDetector mGestureDetector;

    public RecyclerItemClickListerner(Context context, final  RecyclerView recyclerView ,OnItemClickListerner listener)
    {
        mListener=listener;
        mGestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener())
        {

            public boolean onSingleTapUp(MotionEvent e)
            {return true;}


            public void onLongpress(MotionEvent e)
            {
                View childview= recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(childview!=null && mListener!=null)
                {
                    mListener.onItemLongClick(childview,recyclerView.getChildPosition(childview));
                }
            }

        };

    }

    public boolean onInterceptTouchEvent(RecyclerView view,MotionEvent e)
    {
        View childview = view.findChildViewUnder(e.getX(),e.getY());
        if (childview != null && mListener != null && mGestureDetector.onTouchEvent(e))
        {
            mListener.onItemClick(childview, view.getChildPosition(childview));
        }
        return  false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent){}

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
