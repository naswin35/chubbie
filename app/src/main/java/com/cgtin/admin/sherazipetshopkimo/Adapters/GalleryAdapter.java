package com.cgtin.admin.sherazipetshopkimo.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cgtin.admin.sherazipetshopkimo.Classes.ItemImage;
import com.cgtin.admin.sherazipetshopkimo.HomeActivity.GalleryView;
import com.cgtin.admin.sherazipetshopkimo.R;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;


import uk.co.senab.photoview.PhotoView;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

public class GalleryAdapter extends PagerAdapter {

	private Activity _activity;
	private List<ItemImage> _imagePaths;
	private LayoutInflater inflater;

	public GalleryAdapter(Activity activity,
                          List<ItemImage> imagePaths) {
		this._activity = activity;
		this._imagePaths = imagePaths;



        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(_activity.getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);


	}

	@Override
	public int getCount() {
		return this._imagePaths.size();
	}

	@Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

	@Override
    public Object instantiateItem(ViewGroup container, final int position) {

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.pager_gallery_item, container,
                false);

        container.addView(viewLayout);


        int thumbnailSize = 180;


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(130,130);
        params.setMargins(15, 10, 15, 10);



        final SubsamplingScaleImageView thumbView = new SubsamplingScaleImageView(_activity);
        thumbView.setLayoutParams(params);
       // thumbView.setBackgroundColor(ContextCompat.getColor(_activity, R.color.transparent_white));
        thumbView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GalleryView.viewPager.setCurrentItem(position);
            }
        });
        GalleryView.thumbs.addView(thumbView);

        final PhotoView imageView =
                (PhotoView) viewLayout.findViewById(R.id.image);


        Glide.with(_activity)
                .load(_imagePaths.get(position).getImage())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        imageView.setImageBitmap(bitmap);
                        thumbView.setImage(ImageSource.bitmap(bitmap));
                    }
                });



        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}