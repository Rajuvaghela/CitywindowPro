package com.citywindowpro.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.citywindowpro.Model.FiveMainsubcatgory;
import com.citywindowpro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp-pc on 18-03-2016.
 */
public class ViewPagerAdapter2 extends PagerAdapter {

    private Context mContext;

    public List<SliderAdapter> imgArray;

    public ViewPagerAdapter2(Context mContext, List<SliderAdapter> imgArray) {
        this.mContext = mContext;
        this.imgArray = imgArray;
    }

    @Override
    public int getCount() {
        return imgArray.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
        Glide.with(mContext).load(imgArray.get(position).getBottom_slider_image()).into(imageView);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}